package com.example.myapplication.utils

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

object LocationUtils {
    suspend fun getCurrentLocation(context: Context): Location? {
        val client = LocationServices.getFusedLocationProviderClient(context)
        return try {
            client.lastLocation.await()
        } catch (e: Exception) {
            null
        }
    }
}
