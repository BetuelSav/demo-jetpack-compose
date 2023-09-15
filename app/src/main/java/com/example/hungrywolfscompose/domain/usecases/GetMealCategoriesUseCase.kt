package com.example.hungrywolfscompose.domain.usecases

import com.example.hungrywolfscompose.data.models.MealCategories
import com.example.hungrywolfscompose.domain.repository.MainRepository
import com.example.hungrywolfscompose.shared.base.BaseUseCase
import com.example.hungrywolfscompose.shared.base.Result
import com.example.hungrywolfscompose.shared.utils.Constants
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