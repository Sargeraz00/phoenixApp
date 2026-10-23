package com.sargedev.phoenixapp.features.auth.domain

data class AuthResult(
    val isNewUser: Boolean,
    val user: User,
    val accessToken: String,
    val refreshToken: String
)

interface AuthRepository {
    suspend fun loginWithEmail(email: String, pass: String): AuthResult
    suspend fun loginWithGoogle(idToken: String): AuthResult
}
