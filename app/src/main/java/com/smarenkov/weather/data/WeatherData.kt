package com.smarenkov.weather.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

sealed class WeatherData

data class CurrentLocation(
    val date: String = getCurrentDate(),
    val location: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
) : WeatherData()

private fun getCurrentDate(): String {
    val today = Date()
    val formatter = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    return "Today, ${formatter.format(today)}"
}