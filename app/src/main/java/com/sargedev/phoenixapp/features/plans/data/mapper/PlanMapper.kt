package com.sargedev.phoenixapp.features.plans.data.mapper

import com.sargedev.phoenixapp.features.plans.data.dto.GymPlanDto
import com.sargedev.phoenixapp.features.plans.domain.model.GymPlan

fun GymPlanDto.toDomain(): GymPlan {
    return GymPlan(
        id = id,
        displayName = displayName
    )
}
