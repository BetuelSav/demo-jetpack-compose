package com.example.hungrywolfscompose.shared.usecases

import com.example.hungrywolfscompose.data.models.Meals
import com.example.hungrywolfscompose.data.repo.MainRepo
import com.example.hungrywolfscompose.shared.base.BaseUseCase
import com.example.hungrywolfscompose.shared.utils.Constants
import com.example.hungrywolfscompose.shared.base.Result

class GetMealsFromCategoryUseCase(private val repo: MainRepo) : BaseUseCase<String, Meals>() {
    override suspend fun run(params: String): Result<Meals> {
        return try {
            Result.Success(repo.getMealsFromCategory(params))
        } catch (e: Throwable) {
            Result.Error(e.localizedMessage ?: Constants.GENERIC_ERROR)
        }
    }
}