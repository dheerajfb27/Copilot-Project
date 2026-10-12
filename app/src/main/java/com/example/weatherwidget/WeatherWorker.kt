package com.example.weatherwidget

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class WeatherWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val prefs = WeatherPreferences(applicationContext)
        val city = prefs.getCity()
        val apiKey = prefs.getApiKey()
        val weather = WeatherRepository.getWeatherForCity(city, apiKey)
        WeatherRepository.saveWeather(applicationContext, weather)
        return Result.success()
    }
}
