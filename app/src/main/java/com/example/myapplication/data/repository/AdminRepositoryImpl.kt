package com.example.myapplication.data.repository

import com.example.myapplication.domain.model.*
import com.example.myapplication.domain.repository.AdminRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor() : AdminRepository {

    override fun getAdminSession(): Flow<Admin?> = flowOf(Admin(id = "admin_1", name = "Super Admin", role = AdminRole.SUPER_ADMIN))
    override suspend fun adminLogin(email: String, pass: String): Admin = Admin(id = "admin_1", name = "Super Admin", email = email, role = AdminRole.SUPER_ADMIN)
    override suspend fun verifyOtp(otp: String): Boolean = true
    override fun getPendingVerifications(): Flow<List<VerificationRequest>> = flowOf(emptyList())
    override suspend fun processVerification(requestId: String, status: VerificationStatus, note: String?) {}
    override fun getAllHospitals(): Flow<List<Hospital>> = flowOf(emptyList())
    override fun getAllBloodBanks(): Flow<List<BloodBank>> = flowOf(emptyList())
    override fun getAllCaptains(): Flow<List<User>> = flowOf(emptyList())
    override fun getSystemLogs(): Flow<List<SystemLog>> = flowOf(emptyList())
    override fun getFraudAlerts(): Flow<List<FraudAlert>> = flowOf(emptyList())
    override fun getUserReports(): Flow<List<UserReport>> = flowOf(emptyList())
    override suspend fun suspendUser(userId: String, reason: String) {}
    override suspend fun broadcastNotification(title: String, body: String, target: String) {}
    override fun getRealTimeAnalytics(): Flow<AdminAnalytics> = flowOf(AdminAnalytics(totalUsers = 42500, verifiedDonors = 12400, activeEmergencies = 8))
}
