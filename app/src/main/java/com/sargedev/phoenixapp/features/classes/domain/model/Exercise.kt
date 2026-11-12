package com.sargedev.phoenixapp.features.classes.domain.model

data class Exercise(
    val id: String,
    val name: String,
    val sets: Int,
    val repetitions: Int,
    val durationInSec: Float,
    val restSeconds: Float,
    val notes: String
)
