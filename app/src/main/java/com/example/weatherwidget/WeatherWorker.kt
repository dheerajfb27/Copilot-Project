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
        val weather = try {
            WeatherRepository.fetchWeatherByCity(city)
        } catch (e: Exception) {
            WeatherRepository.placeholderWeather(city)
        }
        WeatherRepository.saveWeather(applicationContext, weather)
        return Result.success()
    }
}
