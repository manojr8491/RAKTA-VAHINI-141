package com.example.myapplication.presentation.emergency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.repository.EmergencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyViewModel @Inject constructor(
    private val repository: EmergencyRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _sosResult = MutableStateFlow<Result<String>?>(null)
    val sosResult = _sosResult.asStateFlow()

    fun triggerSOS(lat: Double, lng: Double, bloodGroup: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _sosResult.value = repository.triggerSOS(lat, lng, bloodGroup)
            _isLoading.value = false
        }
    }
}
