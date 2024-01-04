package com.example.data.network

import com.example.data.api.MainAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideMainAPI(
        retrofit: Retrofit
    ): MainAPI = retrofit.create(MainAPI::class.java)
}