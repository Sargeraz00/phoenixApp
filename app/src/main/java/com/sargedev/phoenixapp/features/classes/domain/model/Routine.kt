package com.sargedev.phoenixapp.features.classes.domain.model

data class Routine(
    val id: String,
    val createdBy: String,
    val name: String,
    val category: String,
    val description: String,
    val imageUrl: String,
    val status: String,
    val totalExercises: Int,
    val exercises: List<Exercise>
)