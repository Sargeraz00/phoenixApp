package com.sargedev.phoenixapp.features.profile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class CompleteProfileUiState(
    val plan: String = "",
    val emergencyContact: String = "",
    val phoneNumber: String = "",
    val birthDate: String = "",
    val preexistingConditions: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class CompleteProfileViewModel : ViewModel() {
    var state by mutableStateOf(CompleteProfileUiState())
        private set

    fun onPlanChanged(value: String) {
        state = state.copy(plan = value)
    }

    fun onEmergencyContactChanged(value: String) {
        state = state.copy(emergencyContact = value)
    }

    fun onPhoneNumberChanged(value: String) {
        state = state.copy(phoneNumber = value)
    }

    fun onBirthDateChanged(value: String) {
        state = state.copy(birthDate = value)
    }

    fun onPreexistingConditionsChanged(value: String) {
        state = state.copy(preexistingConditions = value)
    }

    fun submitProfile() {
        // Submit profile logic
    }
}
