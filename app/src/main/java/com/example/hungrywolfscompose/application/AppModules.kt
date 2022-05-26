package com.example.hungrywolfscompose.application

import com.example.hungrywolfscompose.core.main.details.DetailsViewModel
import com.example.hungrywolfscompose.core.main.home.HomeViewModel
import com.example.hungrywolfscompose.core.main.search.SearchViewModel
import com.example.hungrywolfscompose.data.api.ApiProvider
import com.example.hungrywolfscompose.data.repo.MainRepo
import com.example.hungrywolfscompose.data.repo.MainRepoImplementation
import com.example.hungrywolfscompose.shared.usecases.GetMealCategoriesUseCase
import com.example.hungrywolfscompose.shared.usecases.GetMealDetailsUseCase
import com.example.hungrywolfscompose.shared.usecases.GetMealsFromCategoryUseCase
import com.example.hungrywolfscompose.shared.usecases.GetSearchedMealsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules {

    private val viewModels = module {
        viewModel { HomeViewModel(get(), get()) }
        viewModel { (mealId: String) -> DetailsViewModel(mealId, get()) }
        viewModel { SearchViewModel(get()) }
    }

    private val apiModule = module {
        single { ApiProvider.provideMainAPI() }
    }

    private val repoModule = module {
        single<MainRepo> { MainRepoImplementation(get()) }
    }

    private val useCase = module {
        single { GetMealCategoriesUseCase(get()) }
        single { GetMealsFromCategoryUseCase(get()) }
        single { GetMealDetailsUseCase(get()) }
        single { GetSearchedMealsUseCase(get()) }
    }

    val modules = listOf(viewModels, apiModule, repoModule, useCase)
}