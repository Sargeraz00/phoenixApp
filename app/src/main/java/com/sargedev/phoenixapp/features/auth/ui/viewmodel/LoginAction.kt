package com.sargedev.phoenixapp.features.auth.ui.viewmodel

sealed interface LoginAction {
    data class SubmitGoogleLogin(val idToken: String) : LoginAction
    data object DismissError : LoginAction
}
