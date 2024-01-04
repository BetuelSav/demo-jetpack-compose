package com.example.domain.usecases

import com.example.domain.models.MealDetails
import com.example.domain.repository.MainRepository
import com.example.domain.shared.BaseUseCase
import com.example.domain.shared.Constants
import com.example.domain.shared.Result
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