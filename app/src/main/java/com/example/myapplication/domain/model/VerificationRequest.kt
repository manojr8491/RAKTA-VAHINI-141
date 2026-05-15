package com.example.myapplication.domain.model

enum class EntityType {
    USER,
    HOSPITAL,
    BLOOD_BANK,
    CAPTAIN
}

enum class VerificationStatus {
    PENDING,
    UNDER_REVIEW,
    VERIFIED,
    REJECTED,
    SUSPENDED
}

data class VerificationRequest(
    val id: String = "",
    val entityId: String = "",
    val entityType: EntityType = EntityType.USER,
    val status: VerificationStatus = VerificationStatus.PENDING,
    val documents: Map<String, String> = emptyMap(), // Doc Label to URL
    val aiRiskScore: Double = 0.0,
    val submittedAt: Long = System.currentTimeMillis(),
    val reviewedBy: String? = null,
    val reviewNote: String? = null
)
