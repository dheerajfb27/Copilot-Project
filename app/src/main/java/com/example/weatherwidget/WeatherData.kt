package com.example.weatherwidget

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
