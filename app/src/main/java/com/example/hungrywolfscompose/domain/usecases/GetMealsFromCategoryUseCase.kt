package com.example.hungrywolfscompose.domain.usecases

import com.example.hungrywolfscompose.data.models.Meals
import com.example.hungrywolfscompose.domain.repository.MainRepository
import com.example.hungrywolfscompose.shared.base.BaseUseCase
import com.example.hungrywolfscompose.shared.utils.Constants
import com.example.hungrywolfscompose.shared.base.Result
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