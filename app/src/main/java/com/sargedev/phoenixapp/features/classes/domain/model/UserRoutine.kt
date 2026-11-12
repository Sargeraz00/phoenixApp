package com.sargedev.phoenixapp.features.classes.domain.model

data class UserRoutine(
    val id: String,
    val userUid: String,
    val status: String,
    val assignedAt: String,
    val routine: Routine
)