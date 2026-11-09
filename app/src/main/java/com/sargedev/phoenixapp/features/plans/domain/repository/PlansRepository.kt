package com.sargedev.phoenixapp.features.plans.domain.repository

import com.sargedev.phoenixapp.features.plans.domain.model.GymPlan

interface PlansRepository {
    suspend fun getPlans(): Result<List<GymPlan>>
}
