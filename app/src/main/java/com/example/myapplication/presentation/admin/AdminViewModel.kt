package com.example.myapplication.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.*
import com.example.myapplication.domain.repository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: AdminRepository
) : ViewModel() {

    private val _analytics = MutableStateFlow(AdminAnalytics())
    val analytics = _analytics.asStateFlow()

    private val _logs = MutableStateFlow<List<SystemLog>>(emptyList())
    val logs = _logs.asStateFlow()

    private val _pendingVerifications = MutableStateFlow<List<VerificationRequest>>(emptyList())
    val pendingVerifications = _pendingVerifications.asStateFlow()

    private val _fraudAlerts = MutableStateFlow<List<FraudAlert>>(emptyList())
    val fraudAlerts = _fraudAlerts.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getRealTimeAnalytics().collect { _analytics.value = it }
        }
        viewModelScope.launch {
            repository.getSystemLogs().collect { _logs.value = it }
        }
        viewModelScope.launch {
            repository.getPendingVerifications().collect { _pendingVerifications.value = it }
        }
        viewModelScope.launch {
            repository.getFraudAlerts().collect { _fraudAlerts.value = it }
        }
    }
}
