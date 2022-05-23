package com.example.hungrywolfscompose.data.repo

import com.example.hungrywolfscompose.data.api.MainAPI
import com.example.hungrywolfscompose.data.models.MealCategories
import com.example.hungrywolfscompose.data.models.MealDetails
import com.example.hungrywolfscompose.data.models.Meals
import org.koin.core.component.KoinComponent

interface MainRepo {
    suspend fun getMealCategories(): MealCategories
    suspend fun getMealsFromCategory(category: String): Meals
    suspend fun getMealDetails(id: String): MealDetails
    suspend fun getSearchedMeals(text: String): MealDetails
}

class MainRepoImplementation(private val api: MainAPI) : MainRepo, KoinComponent {
    override suspend fun getMealCategories(): MealCategories = api.getMealCategories()
    override suspend fun getMealsFromCategory(category: String): Meals =
        api.getMealsFromCategory(category)

    override suspend fun getMealDetails(id: String): MealDetails = api.getMealDetails(id)
    override suspend fun getSearchedMeals(text: String): MealDetails = api.getSearchedMeals(text)
}