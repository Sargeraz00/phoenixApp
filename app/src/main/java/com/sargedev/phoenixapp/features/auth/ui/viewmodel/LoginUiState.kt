package com.sargedev.phoenixapp.features.auth.ui.viewmodel

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccess: Boolean = false,
    val isNewUser: Boolean = false,
    val userName: String? = null
)
