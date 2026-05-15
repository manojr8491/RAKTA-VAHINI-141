package com.example.myapplication.domain.model

enum class AdminRole {
    SUPER_ADMIN,
    REGIONAL_ADMIN,
    HOSPITAL_ADMIN,
    BLOOD_BANK_ADMIN,
    EMERGENCY_CONTROL_ADMIN
}

data class Admin(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val role: AdminRole = AdminRole.EMERGENCY_CONTROL_ADMIN,
    val region: String? = null,
    val hospitalId: String? = null,
    val bloodBankId: String? = null,
    val lastLoginAt: Long = System.currentTimeMillis(),
    val devices: List<String> = emptyList(),
    val isActive: Boolean = true
)
