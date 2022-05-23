package com.example.hungrywolfscompose.shared.usecases

import com.example.hungrywolfscompose.data.models.MealCategories
import com.example.hungrywolfscompose.data.repo.MainRepo
import com.example.hungrywolfscompose.shared.base.BaseUseCase
import com.example.hungrywolfscompose.shared.base.Result
import com.example.hungrywolfscompose.shared.utils.Constants

class GetMealCategoriesUseCase(private val mainRepo: MainRepo): BaseUseCase<Unit, MealCategories>() {
    override suspend fun run(params: Unit): Result<MealCategories> {
        return try {
            Result.Success(mainRepo.getMealCategories())
        } catch (e: Throwable){
            Result.Error(e.localizedMessage ?: Constants.GENERIC_ERROR)
        }
    }
}