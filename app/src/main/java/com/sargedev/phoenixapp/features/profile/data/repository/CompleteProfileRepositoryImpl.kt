package com.sargedev.phoenixapp.features.profile.data.repository

import com.sargedev.phoenixapp.features.profile.data.datasource.CompleteProfileRemoteDataSource
import com.sargedev.phoenixapp.features.profile.data.mapper.toDto
import com.sargedev.phoenixapp.features.profile.domain.CompleteProfileRepository
import com.sargedev.phoenixapp.features.profile.domain.Profile
import javax.inject.Inject

class CompleteProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: CompleteProfileRemoteDataSource
) : CompleteProfileRepository {
    override suspend fun completeProfile(profile: Profile): Result<Unit> {
        return try {
            remoteDataSource.completeProfile(profile.toDto(), profile.userUid)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
