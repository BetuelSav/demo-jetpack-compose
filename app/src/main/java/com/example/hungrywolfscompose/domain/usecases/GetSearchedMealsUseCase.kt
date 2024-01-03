package com.example.hungrywolfscompose.domain.usecases

import com.example.hungrywolfscompose.data.models.MealDetails
import com.example.hungrywolfscompose.domain.repository.MainRepository
import com.example.hungrywolfscompose.shared.base.BaseUseCase
import com.example.hungrywolfscompose.shared.base.Result
import com.example.hungrywolfscompose.shared.utils.Constants
import javax.inject.Inject

class GetSearchedMealsUseCase @Inject constructor(
    private val repo: MainRepository
) : BaseUseCase<String, MealDetails>() {
    override suspend fun run(params: String): Result<MealDetails> {
        return try {
            Result.Success(repo.getSearchedMeals(params))
        } catch (e: Throwable) {
            Result.Error(e.localizedMessage ?: Constants.GENERIC_ERROR)
        }
    }
}