package com.example.weatherwidget.api

import retrofit2.http.GET
import retrofit2.http.Query

// Geocoding API: https://geocoding-api.open-meteo.com/v1/search?name={city}&count=1
interface GeocodingService {
    @GET("/v1/search")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 1
    ): GeocodingResponse
}

// Open-Meteo forecast API: https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current_weather=true&temperature_unit=celsius
interface OpenMeteoService {
    @GET("/v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("temperature_unit") temperatureUnit: String = "celsius",
        @Query("timezone") timezone: String = "auto"
    ): OpenMeteoResponse
}
