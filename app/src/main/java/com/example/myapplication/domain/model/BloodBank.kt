package com.example.myapplication.domain.model

data class BloodBank(
    val id: String = "",
    val name: String = "",
    val licenseNumber: String = "",
    val address: String = "",
    val contactNumber: String = "",
    val inventory: Map<String, Int> = emptyMap(), // BloodGroup to Units
    val isVerified: Boolean = false,
    val documents: List<String> = emptyList(),
    val registeredAt: Long = System.currentTimeMillis()
)
