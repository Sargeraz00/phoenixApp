package com.sargedev.phoenixapp.features.profile.domain.model

data class UserProfile(
    val uid: String,
    val fullname: String,
    val email: String,
    val role: String,
    val status: String,
    val phoneNumber: String,
    val emergencyContact: String,
    val birthdate: String,
    val precondition: String,
    val photoUrl: String?
)
