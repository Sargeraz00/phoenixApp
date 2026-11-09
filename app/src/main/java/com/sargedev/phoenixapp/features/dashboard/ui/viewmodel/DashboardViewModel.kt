package com.sargedev.phoenixapp.features.dashboard.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sargedev.phoenixapp.core.session.UserSession
import com.sargedev.phoenixapp.features.dashboard.domain.model.AccessQr
import com.sargedev.phoenixapp.features.dashboard.domain.model.UserDashboard
import com.sargedev.phoenixapp.features.dashboard.domain.usecase.GetAccessQrUseCase
import com.sargedev.phoenixapp.features.dashboard.domain.usecase.GetUserDashboardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val userDashboard: UserDashboard? = null,
    val accessQr: AccessQr? = null,
    val remainingTimeText: String = "2H 59M",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getUserDashboardUseCase: GetUserDashboardUseCase,
    private val getAccessQrUseCase: GetAccessQrUseCase,
    private val userSession: UserSession
) : ViewModel() {
    var state by mutableStateOf(DashboardUiState())
        private set

    private var timerJob: Job? = null

    init {
        fetchDashboard()
    }

    fun fetchDashboard() {
        val uid = userSession.userId
        val gymId = userSession.gymId
        if (uid.isNullOrEmpty()) {
            Log.w("AuthDebug", "DashboardViewModel: userSession.userId is empty")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)

            val dashboardDeferred = async { getUserDashboardUseCase(uid, gymId) }
            val accessQrDeferred = async { getAccessQrUseCase(uid, gymId) }

            val dashboardResult = dashboardDeferred.await()
            val accessQrResult = accessQrDeferred.await()

            var newState = state.copy(isLoading = false)

            dashboardResult.onSuccess { dashboard ->
                newState = newState.copy(userDashboard = dashboard)
                Log.d("AuthDebug", "DashboardViewModel: Dashboard loaded successfully for ${dashboard.user.name}")
            }.onFailure { e ->
                Log.e("AuthDebug", "DashboardViewModel: Error loading dashboard: ${e.message}")
            }

            accessQrResult.onSuccess { accessQr ->
                newState = newState.copy(accessQr = accessQr)
                startTimerTicker(accessQr.expiresAtUnix)
                Log.d("AuthDebug", "DashboardViewModel: Access QR loaded successfully with content: ${accessQr.qrContent}")
            }.onFailure { e ->
                Log.e("AuthDebug", "DashboardViewModel: Error loading access QR: ${e.message}")
            }

            state = newState
        }
    }

    private fun startTimerTicker(expiresAtUnix: Long) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                val nowSec = System.currentTimeMillis() / 1000
                val diffSec = expiresAtUnix - nowSec

                if (diffSec <= 0) {
                    state = state.copy(remainingTimeText = "Expirado")
                    break
                }

                val hours = diffSec / 3600
                val minutes = (diffSec % 3600) / 60
                val seconds = diffSec % 60

                val formattedText = if (hours > 0) {
                    "${hours}H ${minutes}M"
                } else if (minutes > 0) {
                    "${minutes}M ${seconds}S"
                } else {
                    "${seconds}S"
                }

                state = state.copy(remainingTimeText = formattedText)
                delay(1000L)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
