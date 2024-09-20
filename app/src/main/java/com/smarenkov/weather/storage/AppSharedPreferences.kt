package com.smarenkov.weather.storage

import android.content.Context
import com.google.gson.Gson
import com.smarenkov.weather.data.CurrentLocation

class AppSharedPreferences(context: Context, private val gson: Gson) {
    private companion object {
        const val PREFS_NAME = "weather_app_prefs"
        const val KEY_CURRENT_LOCATION = "current_location"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveCurrentLocation(currentLocation: CurrentLocation) {
        val json = gson.toJson(currentLocation)
        sharedPreferences.edit().putString(KEY_CURRENT_LOCATION, json).apply()
    }

    fun fetchCurrentLocation(): CurrentLocation? {
        val json = sharedPreferences.getString(KEY_CURRENT_LOCATION, null)
        return gson.fromJson(json, CurrentLocation::class.java)
    }
}