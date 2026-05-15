package com.example.myapplication.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.BloodRequest
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.BloodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: BloodRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _activeRequests = MutableStateFlow<List<BloodRequest>>(emptyList())
    val activeRequests: StateFlow<List<BloodRequest>> = _activeRequests.asStateFlow()

    private val _nearbyDonors = MutableStateFlow<List<User>>(emptyList())
    val nearbyDonors: StateFlow<List<User>> = _nearbyDonors.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getCurrentUser().collect {
                _user.value = it
            }
        }
        
        viewModelScope.launch {
            repository.getActiveRequests().collect {
                _activeRequests.value = it
            }
        }
    }
    
    fun fetchNearbyDonors(lat: Double, lng: Double) {
        viewModelScope.launch {
            repository.getNearbyDonors(lat, lng, 10.0).collect {
                _nearbyDonors.value = it
            }
        }
    }
}
