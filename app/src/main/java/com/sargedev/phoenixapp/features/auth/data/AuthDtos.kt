package com.sargedev.phoenixapp.features.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginRequestDto(
    val idToken: String
)

@Serializable
data class UserDataDto(
    val uid: String,
    val email: String,
    val name: String,
    val role: String,
    val status: String,
    val isFirstLogin: Boolean,
    val photoUrl: String? = null
)

@Serializable
data class GoogleLoginResponseDto(
    val status: String,
    val responseCode: String,
    val message: String,
    val correlationId: String,
    val data: UserDataDto
)
