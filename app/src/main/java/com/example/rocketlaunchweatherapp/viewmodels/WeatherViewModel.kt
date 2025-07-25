package com.example.rocketlaunchweatherapp.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketlaunchweatherapp.R
import com.example.rocketlaunchweatherapp.SAFE_LAUNCH_WIND_SPEED
import com.example.rocketlaunchweatherapp.api.WeatherResponse
import com.example.rocketlaunchweatherapp.api.WeatherService
import com.example.rocketlaunchweatherapp.data.LaunchConditionIconDTO
import com.example.rocketlaunchweatherapp.hoursBetweenTwoTimestamps
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

    fun isCurrentWindsSafe(): Boolean {
        return !(weatherState.value?.data?.currently?.windSpeed!! > SAFE_LAUNCH_WIND_SPEED ||
                weatherState.value?.data?.currently?.windGust!! > SAFE_LAUNCH_WIND_SPEED)
    }

    fun isGoodLaunchDay(): LaunchConditionIconDTO {
        var hoursNotSafe = 0
        var hoursInRowNotSafe = 1
        var time: Long ?= null

        for (it in weatherState.value?.data?.hourly?.data!!) {
            if (it.windSpeed > SAFE_LAUNCH_WIND_SPEED || it.windGust > SAFE_LAUNCH_WIND_SPEED) {
                hoursNotSafe++

                if (time != null) {
                    if (hoursBetweenTwoTimestamps(time, it.time).toInt() == 1) {
                        time = it.time
                        hoursInRowNotSafe++
                    }
                } else {
                    time = it.time
                }
            }
        }

        val color = when (hoursNotSafe) {
            0 -> Color.Green
            in 0..11 -> Color.Yellow
            else -> Color.Red
        }

        val isSafe = when {
            hoursNotSafe == 0 -> true
            hoursNotSafe in 0..11 && hoursInRowNotSafe < 5 -> true
            else -> false
        }

        val icon = when (color) {
            Color.Green -> R.drawable.ic_rocket_launch
            Color.Yellow -> R.drawable.ic_warning
            else -> R.drawable.ic_high_wind
        }

        return LaunchConditionIconDTO(color, icon, isSafe)
        }
    }
