package com.sargedev.phoenixapp.features.auth.domain.model

data class LoginResult(
    val uid: String,
    val email: String,
    val name: String,
    val role: String,
    val status: String,
    val isFirstLogin: Boolean,
    val photoUrl: String?
)
