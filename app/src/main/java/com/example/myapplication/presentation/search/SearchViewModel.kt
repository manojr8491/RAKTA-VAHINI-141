package com.example.myapplication.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.BloodGroup
import com.example.myapplication.domain.model.Hospital
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.BloodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: BloodRepository
) : ViewModel() {

    private val _donors = MutableStateFlow<List<User>>(emptyList())
    val donors: StateFlow<List<User>> = _donors.asStateFlow()

    private val _hospitals = MutableStateFlow<List<Hospital>>(emptyList())
    val hospitals: StateFlow<List<Hospital>> = _hospitals.asStateFlow()

    private val allHospitals = mutableListOf<Hospital>()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            repository.getNearbyDonors(0.0, 0.0, 0.0).collect {
                _donors.value = it
            }
        }
        viewModelScope.launch {
            repository.getAllHospitals().collect {
                allHospitals.clear()
                allHospitals.addAll(it)
                _hospitals.value = it
            }
        }
    }

    fun searchDonors(bloodGroup: BloodGroup, city: String) {
        viewModelScope.launch {
            // Filter donors
            repository.searchDonors(bloodGroup, city).collect {
                _donors.value = it
            }
            // Filter hospitals based on city for demo
            _hospitals.value = allHospitals.filter { it.address.contains(city, ignoreCase = true) }
        }
    }
}
