package com.sargedev.phoenixapp.features.plans.data.repository

import com.sargedev.phoenixapp.features.plans.data.datasource.PlansRemoteDataSource
import com.sargedev.phoenixapp.features.plans.data.mapper.toDomain
import com.sargedev.phoenixapp.features.plans.domain.model.GymPlan
import com.sargedev.phoenixapp.features.plans.domain.repository.PlansRepository
import javax.inject.Inject

class PlansRepositoryImpl @Inject constructor(
    private val remoteDataSource: PlansRemoteDataSource
) : PlansRepository {
    override suspend fun getPlans(): Result<List<GymPlan>> {
        return try {
            val dtos = remoteDataSource.getPlans()
            val plans = dtos.map { it.toDomain() }
            Result.success(plans)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
