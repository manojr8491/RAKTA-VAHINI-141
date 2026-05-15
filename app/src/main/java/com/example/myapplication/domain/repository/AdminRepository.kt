package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.*
import kotlinx.coroutines.flow.Flow

interface AdminRepository {
    // Auth
    fun getAdminSession(): Flow<Admin?>
    suspend fun adminLogin(email: String, pass: String): Admin
    suspend fun verifyOtp(otp: String): Boolean
    
    // Verifications
    fun getPendingVerifications(): Flow<List<VerificationRequest>>
    suspend fun processVerification(requestId: String, status: VerificationStatus, note: String?)
    
    // Management
    fun getAllHospitals(): Flow<List<Hospital>>
    fun getAllBloodBanks(): Flow<List<BloodBank>>
    fun getAllCaptains(): Flow<List<User>>
    
    // Security & Monitoring
    fun getSystemLogs(): Flow<List<SystemLog>>
    fun getFraudAlerts(): Flow<List<FraudAlert>>
    fun getUserReports(): Flow<List<UserReport>>
    
    // Actions
    suspend fun suspendUser(userId: String, reason: String)
    suspend fun broadcastNotification(title: String, body: String, target: String)
    
    // Analytics
    fun getRealTimeAnalytics(): Flow<AdminAnalytics>
}
