package com.example.myapplication.domain.model

data class SystemLog(
    val id: String = "",
    val message: String = "",
    val level: String = "INFO", // INFO, WARNING, ERROR, EMERGENCY
    val timestamp: Long = System.currentTimeMillis(),
    val source: String = "SYSTEM"
)

data class FraudAlert(
    val id: String = "",
    val entityId: String = "",
    val riskType: String = "", // FAKE_ACCOUNT, SPAM_REQUEST, etc.
    val riskScore: Double = 0.0,
    val description: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "OPEN" // OPEN, RESOLVED, DISMISSED
)

data class UserReport(
    val id: String = "",
    val reporterId: String = "",
    val targetId: String = "",
    val reason: String = "",
    val details: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "PENDING"
)

data class AdminAnalytics(
    val totalUsers: Int = 0,
    val verifiedDonors: Int = 0,
    val activeEmergencies: Int = 0,
    val donationSuccessRate: Double = 0.0,
    val bloodStockLevels: Map<String, Int> = emptyMap(),
    val regionalActivity: Map<String, Int> = emptyMap()
)
