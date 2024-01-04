package com.example.domain.usecases

import com.example.domain.models.MealCategories
import com.example.domain.repository.MainRepository
import com.example.domain.shared.BaseUseCase
import com.example.domain.shared.Constants
import com.example.domain.shared.Result
import javax.inject.Inject

class GetMealCategoriesUseCase @Inject constructor(
    private val repo: MainRepository
) : BaseUseCase<Unit, MealCategories>() {
    override suspend fun run(params: Unit): Result<MealCategories> {
        return try {
            Result.Success(repo.getMealCategories())
        } catch (e: Throwable) {
            Result.Error(e.localizedMessage ?: Constants.GENERIC_ERROR)
        }
    }
}