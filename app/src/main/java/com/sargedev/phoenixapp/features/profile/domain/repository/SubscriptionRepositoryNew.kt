package com.sargedev.phoenixapp.features.profile.domain.repository

import com.sargedev.phoenixapp.features.profile.domain.model.UserSubscription

interface SubscriptionRepositoryNew {
    suspend fun getCurrentSubscription(uid: String, gymId: String): Result<UserSubscription>
}
