package com.example.rocketlaunchweatherapp

import android.text.Editable
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun convertTimestamp(timestamp: Long): String {
    val instant = Instant.ofEpochSecond(timestamp)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a")
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}
