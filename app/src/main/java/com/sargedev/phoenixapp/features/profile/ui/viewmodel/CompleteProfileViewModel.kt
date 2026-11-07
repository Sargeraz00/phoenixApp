package com.sargedev.phoenixapp.features.profile.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sargedev.phoenixapp.core.session.UserSession
import com.sargedev.phoenixapp.features.plans.domain.model.GymPlan
import com.sargedev.phoenixapp.features.plans.domain.usecase.GetPlansUseCase
import com.sargedev.phoenixapp.features.profile.domain.model.Profile
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
    val selectedPlanName: String = "",
    val availablePlans: List<GymPlan> = emptyList(),
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
    private val getPlansUseCase: GetPlansUseCase,
    private val userSession: UserSession
) : ViewModel() {
    var state by mutableStateOf(CompleteProfileUiState())
        private set

    private val _eventChannel = Channel<CompleteProfileEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    init {
        loadPlans()
    }

    private fun loadPlans() {
        viewModelScope.launch {
            getPlansUseCase().onSuccess { plans ->
                state = state.copy(availablePlans = plans)
            }.onFailure { e ->
                Log.e("AuthDebug", "Error loading plans: ${e.message}")
            }
        }
    }

    fun onPlanSelected(displayName: String, id: String) {
        state = state.copy(selectedPlanName = displayName, planId = id)
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

            val formattedPhone = if (state.phoneNumber.isNotBlank() && !state.phoneNumber.startsWith("+")) {
                "+52${state.phoneNumber}"
            } else {
                state.phoneNumber
            }

            val formattedEmergency = if (state.emergencyContact.isNotBlank() && !state.emergencyContact.startsWith("+")) {
                "+52${state.emergencyContact}"
            } else {
                state.emergencyContact
            }

            val profile = Profile(
                phoneNumber = formattedPhone,
                emergencyContact = formattedEmergency,
                birthdate = state.birthDate,
                precondition = state.preconditions,
                planId = state.planId,
                userUid = uid
            )
            val result = completeProfileUseCase(profile)
            result.onSuccess {
                state = CompleteProfileUiState(availablePlans = state.availablePlans)
                _eventChannel.send(CompleteProfileEvent.NavigateToHome)
            }.onFailure { e ->
                state = state.copy(isLoading = false)
                val msg = e.localizedMessage ?: "Error al completar el perfil"
                state = state.copy(errorMessage = msg)
                _eventChannel.send(CompleteProfileEvent.ShowError(msg))
            }
        }
    }
}
