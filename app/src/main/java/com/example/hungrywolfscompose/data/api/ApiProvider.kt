package com.example.hungrywolfscompose.data.api

import com.example.hungrywolfscompose.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 30L


object ApiProvider : KoinComponent {

    fun provideMainAPI(): MainAPI = retrofit.create(MainAPI::class.java)

    private val gsonConverterFactory: GsonConverterFactory = let {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        GsonConverterFactory.create(gson)
    }

    private val okHttpClient: OkHttpClient = let {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

        client.build()
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
}