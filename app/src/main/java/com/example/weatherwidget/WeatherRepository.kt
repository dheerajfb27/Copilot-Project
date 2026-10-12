package com.example.weatherwidget

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import java.util.Locale

data class WeatherData(
    val city: String,
    val summary: String,
    val temperature: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val timestamp: Long = System.currentTimeMillis()
) {
    val tempText: String
        get() = "${temperature.toInt()}°"

    val detailText: String
        get() = "$summary · ${temperature.toInt()}°C"
}

object WeatherRepository {
    private const val DEFAULT_CITY = "Tokyo"
    private val gson = Gson()

    fun getCachedWeather(context: Context): WeatherData {
        val prefs = context.getSharedPreferences("weather_widget", Context.MODE_PRIVATE)
        val json = prefs.getString("last_weather", null) ?: return placeholderWeather(DEFAULT_CITY)
        return gson.fromJson(json, WeatherData::class.java)
    }

    fun saveWeather(context: Context, weather: WeatherData) {
        val prefs = context.getSharedPreferences("weather_widget", Context.MODE_PRIVATE)
        prefs.edit { putString("last_weather", gson.toJson(weather)) }
    }

    fun placeholderWeather(city: String = DEFAULT_CITY): WeatherData = WeatherData(
        city = city,
        summary = "Clear",
        temperature = 24.0,
        feelsLike = 25.0,
        minTemp = 20.0,
        maxTemp = 27.0,
        timestamp = System.currentTimeMillis()
    )

    fun getWeatherForCity(city: String, apiKey: String): WeatherData {
        return if (apiKey.isBlank()) {
            placeholderWeather(city)
        } else {
            val cityName = city.ifBlank { DEFAULT_CITY }
            WeatherData(
                city = cityName,
                summary = "Cloudy",
                temperature = 21.0,
                feelsLike = 22.0,
                minTemp = 18.0,
                maxTemp = 25.0,
                timestamp = System.currentTimeMillis()
            )
        }
    }
}
