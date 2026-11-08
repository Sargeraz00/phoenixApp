package com.sargedev.phoenixapp.features.dashboard.data.repository

import com.sargedev.phoenixapp.features.dashboard.data.datasource.UserDashboardRemoteDataSource
import com.sargedev.phoenixapp.features.dashboard.data.mapper.toDomain
import com.sargedev.phoenixapp.features.dashboard.domain.model.AccessQr
import com.sargedev.phoenixapp.features.dashboard.domain.model.UserDashboard
import com.sargedev.phoenixapp.features.dashboard.domain.repository.UserDashboardRepository
import javax.inject.Inject

class UserDashboardRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserDashboardRemoteDataSource
) : UserDashboardRepository {
    override suspend fun getUserDashboard(uid: String, gymId: String?): Result<UserDashboard> {
        return try {
            val response = remoteDataSource.getDashboardUser(uid, gymId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAccessQr(uid: String, gymId: String?): Result<AccessQr> {
        return try {
            val response = remoteDataSource.getAccessQr(uid, gymId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
