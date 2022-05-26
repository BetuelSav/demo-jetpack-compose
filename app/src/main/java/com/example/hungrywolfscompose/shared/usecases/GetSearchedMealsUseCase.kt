package com.example.hungrywolfscompose.shared.usecases

import com.example.hungrywolfscompose.data.models.MealDetails
import com.example.hungrywolfscompose.data.repo.MainRepo
import com.example.hungrywolfscompose.shared.base.BaseUseCase
import com.example.hungrywolfscompose.shared.base.Result
import com.example.hungrywolfscompose.shared.utils.Constants

class GetSearchedMealsUseCase(private val repo: MainRepo) : BaseUseCase<String, MealDetails>() {
    override suspend fun run(params: String): Result<MealDetails> {
        return try {
            Result.Success(repo.getSearchedMeals(params))
        } catch (e: Throwable) {
            Result.Error(e.localizedMessage ?: Constants.GENERIC_ERROR)
        }
    }
}