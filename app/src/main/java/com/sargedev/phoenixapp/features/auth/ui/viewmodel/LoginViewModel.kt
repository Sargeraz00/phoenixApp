package com.sargedev.phoenixapp.features.auth.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sargedev.phoenixapp.features.auth.data.AuthRepositoryImpl
import com.sargedev.phoenixapp.features.auth.domain.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    var state by mutableStateOf(LoginUiState())
        private set

    private val _eventChannel = Channel<LoginEvent>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.SubmitGoogleLogin -> {
                submitGoogleLogin(action.idToken)
            }
            LoginAction.DismissError -> {
                state = state.copy(errorMessage = null)
            }
        }
    }

    private fun submitGoogleLogin(idToken: String) {
        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            try {
                Log.d("AuthDebug", "Submitting Google login to repository...")
                val result = authRepository.loginWithGoogle(idToken)
                Log.d("AuthDebug", "LoginResult received: isFirstLogin=${result.isFirstLogin}, email=${result.email}, uid=${result.uid}")
                state = state.copy(isLoading = false, isLoginSuccess = true)
                if (result.isFirstLogin) {
                    Log.d("AuthDebug", "Navigating to Form (isFirstLogin = true)")
                    _eventChannel.send(LoginEvent.NavigateToForm)
                } else {
                    Log.d("AuthDebug", "Navigating to Home (isFirstLogin = false)")
                    _eventChannel.send(LoginEvent.NavigateToHome)
                }
            } catch (e: Exception) {
                Log.e("AuthDebug", "Error in submitGoogleLogin: ${e.message}", e)
                state = state.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Error al autenticar con Google"
                )
            }
        }
    }
}
