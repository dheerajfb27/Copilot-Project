package com.example.weatherwidget.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return logger
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor())
        .build()

    val geocodingRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://geocoding-api.open-meteo.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val meteorRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val geocodingService: GeocodingService = geocodingRetrofit.create(GeocodingService::class.java)
    val openMeteoService: OpenMeteoService = meteorRetrofit.create(OpenMeteoService::class.java)
}
