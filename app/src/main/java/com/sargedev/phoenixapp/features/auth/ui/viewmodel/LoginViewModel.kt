package com.sargedev.phoenixapp.features.auth.ui.viewmodel

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
            is LoginAction.EmailChanged -> {
                state = state.copy(email = action.value, errorMessage = null)
            }
            is LoginAction.PasswordChanged -> {
                state = state.copy(password = action.value, errorMessage = null)
            }
            LoginAction.SubmitEmailLogin -> {
                submitEmailLogin()
            }
            LoginAction.SubmitGoogleLogin -> {
                submitGoogleLogin()
            }
            LoginAction.DismissError -> {
                state = state.copy(errorMessage = null)
            }
        }
    }

    private fun submitEmailLogin() {
        val email = state.email.trim()
        val password = state.password
        if (email.isBlank() || password.isBlank()) {
            state = state.copy(errorMessage = "Por favor ingresa correo y contraseña.")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            try {
                val result = authRepository.loginWithEmail(email, password)
                state = state.copy(
                    isLoading = false,
                    isLoginSuccess = true,
                    isNewUser = result.isNewUser,
                    userName = result.user.name
                )
                _eventChannel.send(LoginEvent.NavigateToHome)
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Error de autenticación"
                )
            }
        }
    }

    private fun submitGoogleLogin() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, errorMessage = null)
            try {
                val result = authRepository.loginWithGoogle("mock_google_id_token")
                state = state.copy(
                    isLoading = false,
                    isLoginSuccess = true,
                    isNewUser = result.isNewUser,
                    userName = result.user.name
                )
                _eventChannel.send(LoginEvent.NavigateToHome)
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Error con Google Sign-In"
                )
            }
        }
    }
}
