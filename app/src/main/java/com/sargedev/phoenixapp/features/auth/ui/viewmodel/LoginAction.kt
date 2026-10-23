package com.sargedev.phoenixapp.features.auth.ui.viewmodel

sealed interface LoginAction {
    data class EmailChanged(val value: String) : LoginAction
    data class PasswordChanged(val value: String) : LoginAction
    data object SubmitEmailLogin : LoginAction
    data object SubmitGoogleLogin : LoginAction
    data object DismissError : LoginAction
}
