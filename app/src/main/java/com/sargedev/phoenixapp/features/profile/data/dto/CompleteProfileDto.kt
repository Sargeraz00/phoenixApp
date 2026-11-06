package com.sargedev.phoenixapp.features.profile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CompleteProfileRequestDto(
    val phoneNumber: String,
    val emergencyContact: String,
    val birthdate: String,
    val precondition: String,
    val planId: String
)
