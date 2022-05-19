package com.example.hungrywolfscompose.data.api

import com.example.hungrywolfscompose.data.models.MealCategories
import com.example.hungrywolfscompose.data.models.MealDetails
import com.example.hungrywolfscompose.data.models.Meals
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPI{
    @GET("api/json/v1/1/categories.php")
    suspend fun getMealCategories(): MealCategories

    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsFromCategory(@Query("c") category: String) : Meals

    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealDetails(@Query("i") id: String): MealDetails

    @GET("api/json/v1/1/search.php")
    suspend fun getSearchedMeals(@Query("s") text: String): MealDetails
}