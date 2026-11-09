package com.sargedev.phoenixapp.features.profile.data.repository

import com.sargedev.phoenixapp.features.profile.data.datasource.SubscriptionDataSource
import com.sargedev.phoenixapp.features.profile.data.mapper.toDomain
import com.sargedev.phoenixapp.features.profile.domain.model.UserSubscription
import com.sargedev.phoenixapp.features.profile.domain.repository.SubscriptionRepositoryNew
import javax.inject.Inject

class SubscriptionRepositoryNewImpl @Inject constructor(
    private val dataSource: SubscriptionDataSource
) : SubscriptionRepositoryNew {
    override suspend fun getCurrentSubscription(uid: String, gymId: String): Result<UserSubscription> {
        return try {
            val response = dataSource.getCurrentSubscription(uid, gymId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
