package com.example.domain.usecases

import com.example.domain.models.Meals
import com.example.domain.repository.MainRepository
import com.example.domain.shared.BaseUseCase
import com.example.domain.shared.Constants
import com.example.domain.shared.Result
import javax.inject.Inject

class GetMealsFromCategoryUseCase @Inject constructor(
    private val repo: MainRepository
) : BaseUseCase<String, Meals>() {
    override suspend fun run(params: String): Result<Meals> {
        return try {
            Result.Success(repo.getMealsFromCategory(params))
        } catch (e: Throwable) {
            Result.Error(e.localizedMessage ?: Constants.GENERIC_ERROR)
        }
    }
}