package com.sargedev.phoenixapp.features.profile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponseDto(
    val uid: String,
    val fullname: String,
    val email: String,
    val role: String,
    val status: String,
    val phoneNumber: String,
    val emergencyContact: String,
    val birthdate: String,
    val precondition: String,
    val photoUrl: String? = null
)

@Serializable
data class UserProfileResponseDto(
    val status: String,
    val responseCode: String,
    val message: String,
    val correlationId: String,
    val data: UserDataResponseDto
)
