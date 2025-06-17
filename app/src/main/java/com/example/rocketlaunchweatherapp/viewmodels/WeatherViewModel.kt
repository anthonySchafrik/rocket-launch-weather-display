package com.example.rocketlaunchweatherapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketlaunchweatherapp.SAFE_LAUNCH_WIND_SPEED
import com.example.rocketlaunchweatherapp.api.WeatherResponse
import com.example.rocketlaunchweatherapp.api.WeatherService
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {
    private val _weatherState = MutableLiveData(WeatherState())
    val weatherState: MutableLiveData<WeatherState> = _weatherState

    data class WeatherState(
        val isLoading: Boolean = true,
        val data: WeatherResponse? = null,
        val error: String? = null
    )

     fun fetchWeather(lat: String, lon: String) {
        viewModelScope.launch {
            try {
                val response = WeatherService.getWeatherData(lat, lon)

                _weatherState.postValue(WeatherState(false, response, null))

            } catch(e: Exception) {
                println("Error => ${e.message}")

                _weatherState.postValue(WeatherState(false, null, e.message))
            }
        }
    }

    fun isWindsSafe(): Boolean {
        var isSafe = true

        if (
            weatherState.value?.data?.currently?.windSpeed!! > SAFE_LAUNCH_WIND_SPEED ||
            weatherState.value?.data?.currently?.windGust!! > SAFE_LAUNCH_WIND_SPEED
            )
        {
            return false
        }

        for (it in weatherState.value?.data?.hourly?.data!!) {
            if (it.windSpeed > SAFE_LAUNCH_WIND_SPEED || it.windGust > SAFE_LAUNCH_WIND_SPEED) {
                isSafe = false

                break // exits the loop
            }
        }

        return isSafe
    }
}
