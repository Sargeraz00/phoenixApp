package com.sargedev.phoenixapp.features.classes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDto(
    val id: String,
    val name: String,
    val sets: Int,
    val reps: Int,
    val durationSec: Float = 0f,
    val restSec: Float = 0f,
    val notes: String
)
