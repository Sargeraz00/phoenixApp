package com.sargedev.phoenixapp.features.auth.domain

data class LoginResult(
    val uid: String,
    val email: String,
    val name: String,
    val role: String,
    val status: String,
    val isFirstLogin: Boolean,
    val photoUrl: String?
)

interface AuthRepository {
    suspend fun loginWithGoogle(idToken: String): LoginResult
}
