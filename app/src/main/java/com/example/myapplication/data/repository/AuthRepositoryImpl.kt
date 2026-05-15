package com.example.myapplication.data.repository

import com.example.myapplication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    override fun loginWithPhone(phoneNumber: String): Flow<Result<String>> = flowOf(Result.success("demo_user_123"))
    override fun verifyOtp(verificationId: String, otp: String): Flow<Result<Unit>> = flowOf(Result.success(Unit))
    override fun loginWithGoogle(idToken: String): Flow<Result<Unit>> = flowOf(Result.success(Unit))
    override fun loginWithEmail(email: String, pass: String): Flow<Result<Unit>> = flowOf(Result.success(Unit))
    override fun register(name: String, email: String, bloodGroup: String, city: String): Flow<Result<Unit>> = flowOf(Result.success(Unit))
    override fun logout(): Flow<Result<Unit>> = flowOf(Result.success(Unit))
    override fun isUserLoggedIn(): Boolean = false
    override fun getCurrentUserId(): String = "demo_user_123"
}
