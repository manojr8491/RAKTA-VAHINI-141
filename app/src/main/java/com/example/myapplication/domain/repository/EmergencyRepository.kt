package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.BloodRequest
import kotlinx.coroutines.flow.Flow

interface EmergencyRepository {
    suspend fun triggerSOS(lat: Double, lng: Double, bloodGroup: String): Result<String>
    fun getActiveSOS(): Flow<List<BloodRequest>>
    suspend fun resolveSOS(sosId: String): Result<Unit>
    suspend fun trackLiveLocation(sosId: String, lat: Double, lng: Double)
}
