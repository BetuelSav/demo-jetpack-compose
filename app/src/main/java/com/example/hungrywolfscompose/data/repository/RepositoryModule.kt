package com.example.hungrywolfscompose.data.repository

import com.example.hungrywolfscompose.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(
        repoImpl: MainRepositoryImpl
    ): MainRepository
}