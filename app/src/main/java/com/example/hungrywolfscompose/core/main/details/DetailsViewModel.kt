package com.example.hungrywolfscompose.core.main.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hungrywolfscompose.data.models.IngredientsItem
import com.example.hungrywolfscompose.data.models.Meal
import com.example.hungrywolfscompose.data.models.MealDetail
import com.example.hungrywolfscompose.data.models.MealDetails
import com.example.hungrywolfscompose.data.models.MealFavorite
import com.example.hungrywolfscompose.shared.base.Result
import com.example.hungrywolfscompose.shared.usecases.GetMealDetailsUseCase
import com.example.hungrywolfscompose.shared.utils.Constants
import com.example.hungrywolfscompose.shared.utils.persistence.PersistenceService
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val mealId: String,
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
        viewModelScope.launch {
            when (val result = getMealDetailsUseCase.run(mealId)) {
                is Result.Success -> handlerResultSuccess(result.data)
                is Result.Error -> Log.d(Constants.DEBUG_TAG, "getMealDetails: ${result.error}")
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