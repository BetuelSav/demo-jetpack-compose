package com.example.domain.repository

import com.example.domain.models.MealCategories
import com.example.domain.models.MealDetails
import com.example.domain.models.Meals


interface MainRepository {
    suspend fun getMealCategories(): MealCategories
    suspend fun getMealsFromCategory(category: String): Meals
    suspend fun getMealDetails(id: String): MealDetails
    suspend fun getSearchedMeals(text: String): MealDetails
}