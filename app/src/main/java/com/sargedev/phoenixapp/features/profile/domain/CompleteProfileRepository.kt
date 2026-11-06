package com.sargedev.phoenixapp.features.profile.domain

interface CompleteProfileRepository {
    suspend fun completeProfile(profile: Profile): Result<Unit>
}
