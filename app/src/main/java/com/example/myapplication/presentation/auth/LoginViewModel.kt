package com.example.myapplication.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        _isLoggedIn.value = repository.isUserLoggedIn()
    }

    fun login(phoneNumber: String) {
        if (phoneNumber.length < 10) {
            _error.value = "Enter a valid phone number"
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repository.loginWithPhone(phoneNumber).collect { result ->
                _isLoading.value = false
                result.onSuccess {
                    _isLoggedIn.value = true
                }.onFailure {
                    _error.value = it.message ?: "Authentication failed"
                }
            }
        }
    }

    fun register(name: String, email: String, bloodGroup: String, city: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.register(name, email, bloodGroup, city).collect { result ->
                _isLoading.value = false
                if (result.isSuccess) {
                    _isLoggedIn.value = true
                }
            }
        }
    }
}
