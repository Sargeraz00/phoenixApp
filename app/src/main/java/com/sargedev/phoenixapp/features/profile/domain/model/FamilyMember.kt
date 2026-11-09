package com.sargedev.phoenixapp.features.profile.domain.model

data class FamilyMember(
    val id: String,
    val name: String,
    val relationship: String,
    val photoUrl: String?
)
