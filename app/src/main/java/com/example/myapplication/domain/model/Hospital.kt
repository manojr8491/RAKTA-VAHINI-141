package com.example.myapplication.domain.model

data class Hospital(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val distance: String = ""
)
