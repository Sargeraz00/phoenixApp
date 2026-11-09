package com.sargedev.phoenixapp.features.profile.domain.model

data class Profile(
    val phoneNumber: String,
    val emergencyContact: String,
    val birthdate: String,
    val precondition: String,
    val planId: String,
    val userUid: String? = null
)
