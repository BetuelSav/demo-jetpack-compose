package com.example.hungrywolfscompose.application

import com.example.hungrywolfscompose.core.main.home.HomeViewModel
import com.example.hungrywolfscompose.data.api.ApiProvider
import com.example.hungrywolfscompose.data.repo.MainRepo
import com.example.hungrywolfscompose.data.repo.MainRepoImplementation
import com.example.hungrywolfscompose.shared.usecases.GetMealCategoriesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules {

    private val viewModels = module {
        viewModel { HomeViewModel(get()) }
    }

    private val apiModule = module {
        single { ApiProvider.provideMainAPI() }
    }

    private val repoModule = module {
        single<MainRepo> { MainRepoImplementation(get()) }
    }

    private val useCase = module {
        single { GetMealCategoriesUseCase(get()) }
    }

    val modules = listOf(viewModels, apiModule, repoModule, useCase)
}