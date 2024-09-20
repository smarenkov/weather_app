package com.smarenkov.weather.network.repository

import android.annotation.SuppressLint
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.smarenkov.weather.data.CurrentLocation

class WeatherDataRepository {
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        fusedLocationProviderClient: FusedLocationProviderClient,
        onSuccess: (currentLocation: CurrentLocation) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        fusedLocationProviderClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            location ?: onFailure(Exception("Location is null"))
            onSuccess(
                CurrentLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            )
        }.addOnFailureListener {
            onFailure(Exception("Failed to get location"))
        }
    }

    @Suppress("DEPRECATION")
    fun updateAddressText(
        currentLocation: CurrentLocation,
        geocoder: Geocoder
    ): CurrentLocation {
        val latitude = currentLocation.latitude ?: return currentLocation
        val longitude = currentLocation.longitude ?: return currentLocation

        return geocoder.getFromLocation(latitude, longitude, 1)?.let { addresses ->
            val address = addresses.firstOrNull() ?: return currentLocation

            val locality = address.locality.orEmpty()
            val adminArea = address.adminArea.orEmpty()
            val countryName = address.countryName.orEmpty()

            val addressText = listOf(locality, adminArea, countryName)
                .filter { it.isNotEmpty() }
                .joinToString(", ")

            currentLocation.copy(location = addressText)
        } ?: currentLocation
    }
}