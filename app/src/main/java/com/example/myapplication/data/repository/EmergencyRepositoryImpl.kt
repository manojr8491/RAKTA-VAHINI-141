package com.example.myapplication.data.repository

import com.example.myapplication.domain.model.BloodRequest
import com.example.myapplication.domain.model.BloodGroup
import com.example.myapplication.domain.model.EmergencyLevel
import com.example.myapplication.domain.repository.EmergencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class EmergencyRepositoryImpl @Inject constructor() : EmergencyRepository {

    override suspend fun triggerSOS(lat: Double, lng: Double, bloodGroup: String): Result<String> = Result.success("sos_789")

    override fun getActiveSOS(): Flow<List<BloodRequest>> = flowOf(emptyList())

    override suspend fun resolveSOS(sosId: String): Result<Unit> = Result.success(Unit)

    override suspend fun trackLiveLocation(sosId: String, lat: Double, lng: Double) {}
}
