package com.example.weatherwidget.api

import com.google.gson.annotations.SerializedName

data class GeocodingResponse(
    @SerializedName("results") val results: List<GeocodingResult>? = emptyList()
)

data class GeocodingResult(
    val id: Long?,
    val name: String?,
    val latitude: Double?,
    val longitude: Double?,
    val country: String?
)

data class OpenMeteoResponse(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("generationtime_ms") val generationtimeMs: Double,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String,
    @SerializedName("elevation") val elevation: Double,
    @SerializedName("current_weather") val currentWeather: CurrentWeather?
)

data class CurrentWeather(
    @SerializedName("temperature") val temperature: Double,
    @SerializedName("windspeed") val windspeed: Double,
    @SerializedName("winddirection") val winddirection: Double,
    @SerializedName("weathercode") val weathercode: Int,
    @SerializedName("time") val time: String
)
