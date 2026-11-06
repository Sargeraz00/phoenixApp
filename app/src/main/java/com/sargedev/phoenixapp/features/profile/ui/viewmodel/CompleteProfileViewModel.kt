package com.sargedev.phoenixapp.features.profile.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sargedev.phoenixapp.core.session.UserSession
import com.sargedev.phoenixapp.features.profile.domain.Profile
import com.sargedev.phoenixapp.features.profile.domain.usecase.CompleteProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CompleteProfileUiState(
    val phoneNumber: String = "",
    val emergencyContact: String = "",
    val birthDate: String = "",
    val preconditions: String = "",
    val planId: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface CompleteProfileEvent {
    data object NavigateToHome : CompleteProfileEvent
    data class ShowError(val message: String) : CompleteProfileEvent
}

@HiltViewModel
class CompleteProfileViewModel @Inject constructor(
    private val completeProfileUseCase: CompleteProfileUseCase,
    private val userSession: UserSession
) : ViewModel() {
    var state by mutableStateOf(CompleteProfileUiState())
        private set

    private val _eventChannel = Channel<CompleteProfileEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onPlanChanged(value: String) {
        state = state.copy(planId = value)
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
        state = state.copy(preconditions = value)
    }

    fun submitProfile() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            val uid = userSession.userId
            Log.d("AuthDebug", "Submitting profile with UserSession UID: $uid")
            val profile = Profile(
                phoneNumber = state.phoneNumber,
                emergencyContact = state.emergencyContact,
                birthdate = state.birthDate,
                precondition = state.preconditions,
                planId = state.planId,
                userUid = uid
            )
            val result = completeProfileUseCase(profile)
            state = state.copy(isLoading = false)
            result.onSuccess {
                _eventChannel.send(CompleteProfileEvent.NavigateToHome)
            }.onFailure { e ->
                val msg = e.localizedMessage ?: "Error al completar el perfil"
                state = state.copy(errorMessage = msg)
                _eventChannel.send(CompleteProfileEvent.ShowError(msg))
            }
        }
    }
}
