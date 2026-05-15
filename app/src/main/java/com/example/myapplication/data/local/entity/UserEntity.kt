package com.example.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val bloodGroup: String?,
    val role: String,
    val city: String,
    val lastDonationDate: Long?,
    val isAvailable: Boolean,
    val totalDonations: Int,
    val livesSaved: Int,
    val donationStreak: Int
)
