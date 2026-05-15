package com.example.myapplication.domain.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val bloodGroup: BloodGroup? = null,
    val role: UserRole = UserRole.USER,
    val city: String = "",
    val profileImageUrl: String? = null,
    val lastDonationDate: Long? = null,
    val isAvailable: Boolean = true,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val badges: List<String> = emptyList(),
    val totalDonations: Int = 0,
    val livesSaved: Int = 0,
    val donationStreak: Int = 0
)
