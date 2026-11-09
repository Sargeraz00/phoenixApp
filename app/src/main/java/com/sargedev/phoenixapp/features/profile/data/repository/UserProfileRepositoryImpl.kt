package com.sargedev.phoenixapp.features.profile.data.repository

import com.sargedev.phoenixapp.features.profile.data.datasource.UserProfileRemoteDataSource
import com.sargedev.phoenixapp.features.profile.data.mapper.toDomain
import com.sargedev.phoenixapp.features.profile.domain.model.UserProfile
import com.sargedev.phoenixapp.features.profile.domain.repository.UserProfileRepository
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserProfileRemoteDataSource
) : UserProfileRepository {
    override suspend fun getUserProfile(uid: String): Result<UserProfile> {
        return try {
            val response = remoteDataSource.getUserProfile(uid)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
