package com.sargedev.phoenixapp.features.profile.domain.usecase

import com.sargedev.phoenixapp.features.profile.domain.model.UserSubscription
import com.sargedev.phoenixapp.features.profile.domain.repository.SubscriptionRepositoryNew
import javax.inject.Inject

class GetSubscriptionUseCase @Inject constructor(
    private val repository: SubscriptionRepositoryNew
) {
    suspend operator fun invoke(uid: String, gymId: String): Result<UserSubscription> {
        return repository.getCurrentSubscription(uid, gymId)
    }
}
