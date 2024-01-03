package com.example.hungrywolfscompose.core.main.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.hungrywolfscompose.data.models.MealCategories
import com.example.hungrywolfscompose.data.models.Meals
import com.example.hungrywolfscompose.domain.usecases.GetMealCategoriesUseCase
import com.example.hungrywolfscompose.shared.base.Result
import com.example.hungrywolfscompose.domain.usecases.GetMealsFromCategoryUseCase
import com.example.hungrywolfscompose.shared.utils.Constants.DEBUG_TAG
import com.example.hungrywolfscompose.shared.utils.extensions.performApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMealCategoriesUseCase: GetMealCategoriesUseCase,
    private val getMealsFromCategoryUseCase: GetMealsFromCategoryUseCase
) : ViewModel() {

    private val _mealCategories = mutableStateOf<MealCategories>(MealCategories(emptyList()))
    val mealCategories: State<MealCategories> = _mealCategories

    private val _meals = mutableStateOf<Meals>(Meals(emptyList()))
    val meals: State<Meals> = _meals

    init {
        getMealCategories()
    }

    private fun getMealCategories() {
        performApiCall {
            when (val result = getMealCategoriesUseCase.run(Unit)) {
                is Result.Success ->
                    _mealCategories.value = result.data.also { mealCategories ->
                        mealCategories.categories.firstOrNull()?.category?.let { category ->
                            getMealsFromCategory(category)
                        }
                    }

                is Result.Error -> Log.d(DEBUG_TAG, "getMealCategories: ${result.error}")
                else -> {}
            }
        }
    }

    fun getMealsFromCategory(category: String) {
        performApiCall {
            when (val result = getMealsFromCategoryUseCase.run(category)) {
                is Result.Success -> _meals.value = result.data
                is Result.Error -> Log.d(DEBUG_TAG, "getMealsFromCategory: ${result.error}")
                else -> {}
            }
        }
    }
}