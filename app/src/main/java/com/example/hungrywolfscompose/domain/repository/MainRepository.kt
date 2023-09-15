package com.example.hungrywolfscompose.domain.repository

import com.example.hungrywolfscompose.data.models.MealCategories
import com.example.hungrywolfscompose.data.models.MealDetails
import com.example.hungrywolfscompose.data.models.Meals

interface MainRepository {
    suspend fun getMealCategories(): MealCategories
    suspend fun getMealsFromCategory(category: String): Meals
    suspend fun getMealDetails(id: String): MealDetails
    suspend fun getSearchedMeals(text: String): MealDetails
}