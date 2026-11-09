package com.sargedev.phoenixapp.features.auth.ui.viewmodel

sealed interface LoginEvent {
    data object NavigateToForm : LoginEvent
    data object NavigateToHome : LoginEvent
    data class ShowError(val message: String) : LoginEvent
}
