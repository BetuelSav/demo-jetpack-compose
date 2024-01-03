package com.example.hungrywolfscompose.data.repository

import com.example.hungrywolfscompose.data.api.MainAPI
import com.example.hungrywolfscompose.data.models.MealCategories
import com.example.hungrywolfscompose.data.models.MealDetails
import com.example.hungrywolfscompose.data.models.Meals
import com.example.hungrywolfscompose.domain.repository.MainRepository
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