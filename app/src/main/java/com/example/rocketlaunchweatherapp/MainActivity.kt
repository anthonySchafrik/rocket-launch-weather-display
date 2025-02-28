package com.example.rocketlaunchweatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rocketlaunchweatherapp.fragments.GeocodingDataDisplayFragment
import com.example.rocketlaunchweatherapp.fragments.WeatherDataDisplayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Load Geocoding Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.geocoding_data_display, GeocodingDataDisplayFragment())
            .commit()

        // Load Weather Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.weather_data_display, WeatherDataDisplayFragment())
            .commit()
    }
}