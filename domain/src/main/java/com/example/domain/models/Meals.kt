package com.example.domain.models

import com.google.gson.annotations.SerializedName

data class Meals (@SerializedName("meals") val meals: List<Meal>)

data class Meal(
    @SerializedName("strMeal") val mealName: String,
    @SerializedName("strMealThumb") val mealImageUrl: String,
    @SerializedName("idMeal") val id: String
)