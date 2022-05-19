package com.example.hungrywolfscompose.core.main.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hungrywolfscompose.data.models.MealCategories
import com.example.hungrywolfscompose.shared.usecases.GetMealCategoriesUseCase
import kotlinx.coroutines.launch
import com.example.hungrywolfscompose.shared.base.Result
import com.example.hungrywolfscompose.shared.utils.Constants.DEBUG_TAG

class HomeViewModel(private val getMealCategoriesUseCase: GetMealCategoriesUseCase): ViewModel() {
    private val _mealCategories = mutableStateOf<MealCategories>(MealCategories(emptyList()))
    val mealCategories: State<MealCategories> = _mealCategories

    init {
        getMealCategories()
    }

    private fun getMealCategories() {
        viewModelScope.launch {
            when (val result = getMealCategoriesUseCase.run(Unit)) {
                is Result.Success -> {
                    _mealCategories.value = result.data
                    Log.d(DEBUG_TAG, "getMealCategories: ${result.data}")
                }
                is Result.Error -> {
                    Log.d(DEBUG_TAG, "getMealCategories: ${result.error}")
                }
            }
        }
    }
}