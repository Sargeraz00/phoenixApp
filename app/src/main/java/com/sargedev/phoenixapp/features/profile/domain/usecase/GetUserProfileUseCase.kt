package com.sargedev.phoenixapp.features.profile.domain.usecase

import com.sargedev.phoenixapp.features.profile.domain.model.UserProfile
import com.sargedev.phoenixapp.features.profile.domain.repository.UserProfileRepository
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(uid: String): Result<UserProfile> {
        return repository.getUserProfile(uid)
    }
}
