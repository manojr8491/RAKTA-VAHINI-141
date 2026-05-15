package com.example.myapplication.domain.model

enum class EmergencyLevel {
    NORMAL,
    URGENT,
    CRITICAL
}

enum class RequestStatus {
    PENDING,
    APPROVED,
    COMPLETED,
    CANCELLED
}

data class BloodRequest(
    val id: String = "",
    val seekerId: String = "",
    val seekerName: String = "",
    val bloodGroup: BloodGroup,
    val unitsNeeded: Int = 1,
    val hospitalName: String = "",
    val hospitalAddress: String = "",
    val hospitalLatitude: Double? = null,
    val hospitalLongitude: Double? = null,
    val emergencyLevel: EmergencyLevel = EmergencyLevel.NORMAL,
    val note: String = "",
    val status: RequestStatus = RequestStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis(),
    val approvedBy: String? = null
)
