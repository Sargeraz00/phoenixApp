package com.sargedev.phoenixapp.features.classes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RoutineDto(
    val id: String,
    val createdBy: String,
    val name: String,
    val category: String,
    val description: String,
    val imageUrl: String,
    val status: String,
    val totalExercises: Int,
    val exercises: List<ExerciseDto>
)
