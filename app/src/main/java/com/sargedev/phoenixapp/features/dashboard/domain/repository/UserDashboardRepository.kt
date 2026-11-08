package com.sargedev.phoenixapp.features.dashboard.domain.repository

import com.sargedev.phoenixapp.features.dashboard.domain.model.AccessQr
import com.sargedev.phoenixapp.features.dashboard.domain.model.UserDashboard

interface UserDashboardRepository {
    suspend fun getUserDashboard(uid: String, gymId: String? = null): Result<UserDashboard>
    suspend fun getAccessQr(uid: String, gymId: String? = null): Result<AccessQr>
}
