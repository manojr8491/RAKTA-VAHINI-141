package com.example.myapplication.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun loginWithPhone(phoneNumber: String): Flow<Result<String>> // Returns verificationId
    fun verifyOtp(verificationId: String, otp: String): Flow<Result<Unit>>
    fun loginWithGoogle(idToken: String): Flow<Result<Unit>>
    fun loginWithEmail(email: String, pass: String): Flow<Result<Unit>>
    fun register(name: String, email: String, bloodGroup: String, city: String): Flow<Result<Unit>>
    fun logout(): Flow<Result<Unit>>
    fun isUserLoggedIn(): Boolean
    fun getCurrentUserId(): String?
}
