package com.sargedev.phoenixapp.features.classes.data.dto

import com.sargedev.phoenixapp.features.classes.domain.model.Routine
import kotlinx.serialization.Serializable

@Serializable
data class ClassDto(
    val id: String,
    val userUid: String,
    val status: String,
    val assignedAt: String,
    val routine: RoutineDto
)
