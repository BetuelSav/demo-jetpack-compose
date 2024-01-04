package com.example.hungrywolfscompose.core.main.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.hungrywolfscompose.core.main.NavArgs
import com.example.domain.models.MealDetail
import com.example.domain.models.MealDetails
import com.example.domain.models.MealFavorite
import com.example.domain.shared.Result
import com.example.domain.usecases.GetMealDetailsUseCase
import com.example.hungrywolfscompose.shared.utils.Constants
import com.example.hungrywolfscompose.shared.utils.extensions.performApiCall
import com.example.hungrywolfscompose.shared.utils.persistence.PersistenceService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMealDetailsUseCase: GetMealDetailsUseCase
) : ViewModel(), PersistenceService {

    private val _mealDetails = mutableStateOf<MealDetail?>(null)
    val mealDetails: State<MealDetail?> = _mealDetails

    private val _ingredientsList = mutableStateOf<List<IngredientsItem>?>(null)
    val ingredientsList: State<List<IngredientsItem>?> = _ingredientsList

    private val _isMealFavorite = mutableStateOf<Boolean>(false)
    val isMealFavorite: State<Boolean> = _isMealFavorite

    init {
        getMealDetails()
    }

    private fun getMealDetails() {
        performApiCall {
            val mealId = savedStateHandle.get<String>(NavArgs.MEAL_ID) ?: return@performApiCall
            when (val result = getMealDetailsUseCase.run(mealId)) {
                is Result.Success -> handlerResultSuccess(result.data)
                is Result.Error -> Log.d(Constants.DEBUG_TAG, "getMealDetails: ${result.error}")
                else -> {}
            }
        }
    }

    private fun handlerResultSuccess(result: MealDetails) {
        _mealDetails.value = result.mealDetails?.firstOrNull()
        _isMealFavorite.value = favoriteMeals?.any { it.id == _mealDetails.value?.id } == true
        getIngredientsList()
    }

    private fun getIngredientsList() {
        _ingredientsList.value =
            _mealDetails.value?.ingredientsList?.mapIndexed { index, ingredient ->
                IngredientsItem(
                    measurement = _mealDetails.value?.measurementsList?.get(index) ?: "",
                    ingredient = ingredient ?: ""
                )
            }
    }

    fun getMealTags(): List<String>? =
        _mealDetails.value?.tags?.split(",")?.filter { it.isNotEmpty() }

    fun ingredientChecked(checked: Boolean, index: Int) {
        _ingredientsList.value?.get(index)?.checked = checked
    }

    fun toggleFavoriteMeal() {
        _mealDetails.value?.let { currentMeal ->
            val newFavoriteMeals = favoriteMeals?.toMutableList() ?: mutableListOf()
            if (_isMealFavorite.value) {
                favoriteMeals?.indexOfFirst { it.id == currentMeal.id }?.let { index ->
                    newFavoriteMeals.removeAt(index)
                }
            } else
                newFavoriteMeals.add(
                    MealFavorite(
                        id = currentMeal.id,
                        imageUrl = currentMeal.imageUrl,
                        name = currentMeal.name
                    )
                )
            _isMealFavorite.value = newFavoriteMeals.any { it.id == currentMeal.id } == true
            favoriteMeals = newFavoriteMeals
        }
    }
}