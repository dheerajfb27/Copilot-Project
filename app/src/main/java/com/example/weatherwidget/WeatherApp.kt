package com.example.weatherwidget

import android.app.Application
import android.content.Context
import androidx.core.content.edit

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}
