package com.sargedev.phoenixapp.features.plans.domain.usecase

import com.sargedev.phoenixapp.features.plans.domain.model.GymPlan
import com.sargedev.phoenixapp.features.plans.domain.repository.PlansRepository
import javax.inject.Inject

class GetPlansUseCase @Inject constructor(
    private val plansRepository: PlansRepository
) {
    suspend operator fun invoke(): Result<List<GymPlan>> {
        return plansRepository.getPlans()
    }
}
