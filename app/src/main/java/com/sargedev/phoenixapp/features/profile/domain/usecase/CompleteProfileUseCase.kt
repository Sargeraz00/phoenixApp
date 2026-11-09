package com.sargedev.phoenixapp.features.profile.domain.usecase

import com.sargedev.phoenixapp.features.profile.domain.repository.CompleteProfileRepository
import com.sargedev.phoenixapp.features.profile.domain.model.Profile
import javax.inject.Inject

class CompleteProfileUseCase @Inject constructor(
    private val repository: CompleteProfileRepository
) {
    suspend operator fun invoke(profile: Profile): Result<Unit> {
        return repository.completeProfile(profile)
    }
}
