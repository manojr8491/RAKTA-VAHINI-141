package com.example.myapplication.data.repository

import com.example.myapplication.domain.model.*
import com.example.myapplication.domain.repository.BloodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class BloodRepositoryImpl @Inject constructor() : BloodRepository {

    override fun getCurrentUser(): Flow<User?> = flowOf(
        User(
            uid = "demo_user_123",
            name = "Manoj Manu",
            email = "manoj@example.com",
            bloodGroup = BloodGroup.O_POSITIVE,
            role = UserRole.USER,
            city = "Bangalore",
            lastDonationDate = System.currentTimeMillis() - (100L * 24 * 60 * 60 * 1000),
            livesSaved = 5,
            donationStreak = 3
        )
    )

    override suspend fun createUser(user: User) {}
    override suspend fun updateLocation(lat: Double, lng: Double) {}

    override fun getNearbyDonors(lat: Double, lng: Double, radiusInKm: Double): Flow<List<User>> = flowOf(
        listOf(
            User(uid = "d1", name = "Rahul Sharma", bloodGroup = BloodGroup.A_POSITIVE, city = "Bangalore", phoneNumber = "9876543210"),
            User(uid = "d2", name = "Priya Singh", bloodGroup = BloodGroup.O_POSITIVE, city = "Delhi", phoneNumber = "9876543211"),
            User(uid = "d3", name = "Amit Verma", bloodGroup = BloodGroup.B_NEGATIVE, city = "Mumbai", phoneNumber = "9876543212")
        )
    )

    override fun searchDonors(bloodGroup: BloodGroup, city: String): Flow<List<User>> = flowOf(
        listOf(
            User(uid = "s1", name = "Suresh Raina", bloodGroup = bloodGroup, city = city, phoneNumber = "9988776655", isAvailable = true)
        )
    )

    override suspend fun raiseBloodRequest(request: BloodRequest) {}

    override fun getActiveRequests(): Flow<List<BloodRequest>> = flowOf(
        listOf(
            BloodRequest(id = "r1", hospitalName = "AIIMS Delhi", bloodGroup = BloodGroup.O_POSITIVE, unitsNeeded = 2, emergencyLevel = EmergencyLevel.CRITICAL)
        )
    )

    override suspend fun updateRequestStatus(requestId: String, status: String) {}
    override suspend fun toggleAvailability(isAvailable: Boolean) {}

    override fun getAllHospitals(): Flow<List<Hospital>> = flowOf(
        listOf(
            Hospital("h1", "AIIMS", "New Delhi", "011-26588500", distance = "2.4 km"),
            Hospital("h2", "Apollo Hospital", "Bangalore", "080-26304050", distance = "5.1 km")
        )
    )
}
