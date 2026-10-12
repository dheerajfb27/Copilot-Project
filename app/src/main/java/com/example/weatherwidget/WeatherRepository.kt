package com.example.weatherwidget

import android.content.Context
import android.util.Log
import com.example.weatherwidget.api.OpenMeteoResponse
import com.example.weatherwidget.api.RetrofitClient

object WeatherRepository {
    private const val DEFAULT_CITY = "Tokyo"
    private const val PREFS = "weather_widget"
    private const val KEY_LAST = "last_weather"
    private const val TAG = "WeatherRepository"

    fun getCachedWeather(context: Context): WeatherData {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_LAST, null)
        return if (json == null) {
            placeholderWeather(DEFAULT_CITY)
        } else {
            try {
                GsonHolder.gson.fromJson(json, WeatherData::class.java)
            } catch (e: Exception) {
                Log.w(TAG, "Failed to parse cached weather", e)
                placeholderWeather(DEFAULT_CITY)
            }
        }
    }

    fun saveWeather(context: Context, weather: WeatherData) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LAST, GsonHolder.gson.toJson(weather)).apply()
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

    // Suspend functions for fetching live data
    suspend fun fetchWeatherByCity(city: String): WeatherData {
        val geoResp = try {
            RetrofitClient.geocodingService.searchCity(city, 1)
        } catch (e: Exception) {
            Log.w(TAG, "Geocoding failed, using default city", e)
            return placeholderWeather(city)
        }

        val result = geoResp.results?.firstOrNull()
            ?: return placeholderWeather(city)

        val lat = result.latitude ?: return placeholderWeather(city)
        val lon = result.longitude ?: return placeholderWeather(city)
        return fetchWeatherByCoords(lat, lon, result.name ?: city)
    }

    suspend fun fetchWeatherByCoords(lat: Double, lon: Double, cityName: String = DEFAULT_CITY): WeatherData {
        val resp: OpenMeteoResponse = try {
            RetrofitClient.openMeteoService.getCurrentWeather(
                latitude = lat,
                longitude = lon
            )
        } catch (e: Exception) {
            Log.w(TAG, "Open-Meteo fetch failed", e)
            return placeholderWeather(cityName)
        }

        val cw = resp.currentWeather
        if (cw == null) return placeholderWeather(cityName)

        val summary = mapWeatherCodeToSummary(cw.weathercode)
        val weather = WeatherData(
            city = cityName,
            summary = summary,
            temperature = cw.temperature,
            feelsLike = cw.temperature,
            minTemp = cw.temperature,
            maxTemp = cw.temperature,
            timestamp = System.currentTimeMillis()
        )
        return weather
    }

    private fun mapWeatherCodeToSummary(code: Int): String {
        return when (code) {
            0 -> "Clear"
            in 1..3 -> "Partly cloudy"
            in 45..48 -> "Fog"
            in 51..67 -> "Drizzle"
            in 71..77 -> "Snow"
            in 80..82 -> "Rain"
            in 95..99 -> "Thunderstorm"
            else -> "Unknown"
        }
    }
}
