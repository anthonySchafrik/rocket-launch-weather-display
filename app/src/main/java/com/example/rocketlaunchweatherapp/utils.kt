package com.example.rocketlaunchweatherapp

import android.text.Editable
import android.util.TypedValue
import android.view.View
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

fun convertTimestamp(timestamp: Long): String {
    val instant = Instant.ofEpochSecond(timestamp)
    val formatter = DateTimeFormatter.ofPattern("h a")
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}


fun getThemeTextColor(view: View): Int {
    val typedValue = TypedValue()
    val theme = view.context.theme

    theme.resolveAttribute(android.R.attr.textColorPrimary, typedValue, true)

    return view.context.getColor(typedValue.resourceId)
}

fun hoursBetweenTwoTimestamps(timestamp1: Long, timestamp2: Long): Double {
    // Calculate time difference in hours
    val diffInSeconds = Math.abs(timestamp2 - timestamp1)
    val diffInHours = diffInSeconds / 3600.0

    return diffInHours
}
