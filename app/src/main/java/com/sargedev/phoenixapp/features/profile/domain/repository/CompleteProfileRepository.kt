package com.sargedev.phoenixapp.features.profile.domain.repository

import com.sargedev.phoenixapp.features.profile.domain.model.Profile

interface CompleteProfileRepository {
    suspend fun completeProfile(profile: Profile): Result<Unit>
}
