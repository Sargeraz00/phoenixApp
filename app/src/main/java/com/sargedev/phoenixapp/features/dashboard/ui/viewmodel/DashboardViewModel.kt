package com.sargedev.phoenixapp.features.dashboard.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sargedev.phoenixapp.core.session.UserSession
import com.sargedev.phoenixapp.features.profile.domain.model.UserProfile
import com.sargedev.phoenixapp.features.profile.domain.usecase.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val userProfile: UserProfile? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val userSession: UserSession
) : ViewModel() {
    var state by mutableStateOf(DashboardUiState())
        private set

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        val uid = userSession.userId
        if (uid.isNullOrEmpty()) {
            Log.w("AuthDebug", "DashboardViewModel: userSession.userId is empty")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            getUserProfileUseCase(uid).onSuccess { profile ->
                state = state.copy(userProfile = profile, isLoading = false)
                Log.d("AuthDebug", "DashboardViewModel: Profile loaded successfully for ${profile.fullname}")
            }.onFailure { e ->
                state = state.copy(isLoading = false, errorMessage = e.localizedMessage)
                Log.e("AuthDebug", "DashboardViewModel: Error loading profile: ${e.message}")
            }
        }
    }
}
