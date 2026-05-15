package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.BloodGroup
import com.example.myapplication.domain.model.BloodRequest
import com.example.myapplication.domain.model.Hospital
import com.example.myapplication.domain.model.User
import kotlinx.coroutines.flow.Flow

interface BloodRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun createUser(user: User)
    suspend fun updateLocation(lat: Double, lng: Double)
    fun getNearbyDonors(lat: Double, lng: Double, radiusInKm: Double): Flow<List<User>>
    fun searchDonors(bloodGroup: BloodGroup, city: String): Flow<List<User>>
    suspend fun raiseBloodRequest(request: BloodRequest)
    fun getActiveRequests(): Flow<List<BloodRequest>>
    suspend fun updateRequestStatus(requestId: String, status: String)
    suspend fun toggleAvailability(isAvailable: Boolean)
    fun getAllHospitals(): Flow<List<Hospital>>
}
