package com.example.myapplication.presentation.donor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.BloodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonorViewModel @Inject constructor(
    private val repository: BloodRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getCurrentUser().collect {
                _user.value = it
            }
        }
    }

    fun toggleAvailability(isAvailable: Boolean) {
        viewModelScope.launch {
            repository.toggleAvailability(isAvailable)
        }
    }
}
