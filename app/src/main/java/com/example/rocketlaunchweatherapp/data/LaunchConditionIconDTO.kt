package com.example.rocketlaunchweatherapp.data

import com.example.rocketlaunchweatherapp.R
import androidx.compose.ui.graphics.Color

data class LaunchConditionIconDTO(
    var color: Color = Color.Red,
    var icon: Int = R.drawable.ic_high_wind,
    var isSafe: Boolean = false
)
