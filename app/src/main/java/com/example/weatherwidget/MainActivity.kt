package com.example.weatherwidget

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var cityText: TextView
    private lateinit var tempText: TextView
    private lateinit var conditionText: TextView
    private lateinit var cityInput: EditText
    private lateinit var refreshButton: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val preferences by lazy { WeatherPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityText = findViewById(R.id.cityText)
        tempText = findViewById(R.id.tempText)
        conditionText = findViewById(R.id.conditionText)
        cityInput = findViewById(R.id.cityInput)
        refreshButton = findViewById(R.id.refreshButton)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        cityInput.setText(preferences.getCity())
        refreshButton.setOnClickListener {
            val city = cityInput.text.toString().trim()
            if (city.isNotEmpty()) {
                preferences.setCity(city)
                showWeather(WeatherRepository.getWeatherForCity(city, preferences.getApiKey()))
            } else {
                requestLocationWeather()
            }
        }

        showWeather(WeatherRepository.getCachedWeather(this))
        requestLocationWeather()
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationWeather() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1001
            )
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            val city = preferences.getCity()
            val weather = if (location != null) {
                WeatherRepository.getWeatherForCity(city, preferences.getApiKey())
            } else {
                WeatherRepository.getWeatherForCity(city, preferences.getApiKey())
            }
            showWeather(weather)
        }
    }

    private fun showWeather(weather: WeatherData) {
        cityText.text = weather.city
        tempText.text = weather.tempText
        conditionText.text = weather.summary
        WeatherRepository.saveWeather(this, weather)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocationWeather()
        }
    }
}
