package com.example.hungrywolfscompose.shared.utils.persistence

import android.util.Log
import com.example.domain.models.MealFavorite
import com.example.hungrywolfscompose.shared.utils.Constants
import com.orhanobut.hawk.Hawk

interface PersistenceService {
    var favoriteMeals: List<MealFavorite>?
        get() = Hawk.get(HawkKeys.MEAL_FAVORITE)
        set(meals) {
            Hawk.put(HawkKeys.MEAL_FAVORITE, meals)
            Log.d(Constants.DEBUG_TAG, "favoriteMeals: $meals")
        }
}