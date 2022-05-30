package com.example.hungrywolfscompose.core.main.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.hungrywolfscompose.data.models.MealDetails
import com.example.hungrywolfscompose.shared.base.Result
import com.example.hungrywolfscompose.shared.usecases.GetSearchedMealsUseCase
import com.example.hungrywolfscompose.shared.utils.Constants
import com.example.hungrywolfscompose.shared.utils.extensions.performApiCall

class SearchViewModel(
    private val getSearchedMealsUseCase: GetSearchedMealsUseCase
) : ViewModel() {

    private val _meals = mutableStateOf<MealDetails>(MealDetails(emptyList()))
    val meals: State<MealDetails> = _meals

    init {
        getMealsForQuery()
    }

    fun getMealsForQuery(query: String = "") {
        performApiCall(showLoading = false) {
            when (val result = getSearchedMealsUseCase.run(query)) {
                is Result.Success -> _meals.value = result.data.also {
                    Log.d(
                        Constants.DEBUG_TAG,
                        "getMealsFromQuery: ${result.data.mealDetails}"
                    )
                }
                is Result.Error -> Log.d(Constants.DEBUG_TAG, "getMealsFromQuery: ${result.error}")
            }
        }
    }
}