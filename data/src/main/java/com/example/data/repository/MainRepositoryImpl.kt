package com.example.data.repository

import com.example.data.api.MainAPI
import com.example.domain.models.MealCategories
import com.example.domain.models.MealDetails
import com.example.domain.models.Meals
import com.example.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val api: MainAPI
) : MainRepository {
    override suspend fun getMealCategories(): MealCategories = api.getMealCategories()
    override suspend fun getMealsFromCategory(category: String): Meals =
        api.getMealsFromCategory(category)

    override suspend fun getMealDetails(id: String): MealDetails = api.getMealDetails(id)
    override suspend fun getSearchedMeals(text: String): MealDetails = api.getSearchedMeals(text)
}