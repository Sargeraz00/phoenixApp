package com.sargedev.phoenixapp.features.plans.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GymPlanDto(
    val id: String,
    val displayName: String
)
