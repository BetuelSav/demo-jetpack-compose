package com.example.hungrywolfscompose.core.main.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.hungrywolfscompose.data.models.MealFavorite
import com.example.hungrywolfscompose.shared.utils.persistence.PersistenceService

class FavoriteViewModel: ViewModel(), PersistenceService {
    private val _meals = mutableStateOf(favoriteMeals)
    val meals: State<List<MealFavorite>?> = _meals

    fun updateFavoriteMealList(){
        _meals.value = favoriteMeals
    }

    fun deleteMeal(index: Int){
        favoriteMeals = favoriteMeals?.toMutableList()?.apply { removeAt(index) }
        updateFavoriteMealList()
    }
}