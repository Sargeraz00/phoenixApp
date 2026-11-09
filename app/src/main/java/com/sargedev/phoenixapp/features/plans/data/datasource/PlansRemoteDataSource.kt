package com.sargedev.phoenixapp.features.plans.data.datasource

import com.sargedev.phoenixapp.features.plans.data.dto.GymPlanDto
import io.ktor.client.*
import javax.inject.Inject

class PlansRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    // Ready for future endpoint (e.g. httpClient.get("$baseUrl/plans").body())
    suspend fun getPlans(): List<GymPlanDto> {
        // Fallback / static data until endpoint is available
        return listOf(
            GymPlanDto("plan_basico", "Plan Básico - $499 / mes"),
            GymPlanDto("plan_pro", "Plan Pro - $899 / mes"),
            GymPlanDto("plan_vip", "Plan VIP Anual - $7,999 / año"),
            GymPlanDto("plan_estudiante", "Plan Estudiante - $399 / mes")
        )
    }
}
