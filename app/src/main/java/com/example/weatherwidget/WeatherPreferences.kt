package com.example.weatherwidget

import android.content.Context
import androidx.core.content.edit

class WeatherPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("weather_widget", Context.MODE_PRIVATE)

    fun getCity(): String = prefs.getString("city", "Tokyo") ?: "Tokyo"
    fun setCity(city: String) = prefs.edit { putString("city", city) }

    fun getApiKey(): String = prefs.getString("api_key", "") ?: ""
    fun setApiKey(key: String) = prefs.edit { putString("api_key", key) }
}
