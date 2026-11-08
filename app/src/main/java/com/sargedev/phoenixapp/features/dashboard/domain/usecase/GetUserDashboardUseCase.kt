package com.sargedev.phoenixapp.features.dashboard.domain.usecase

import com.sargedev.phoenixapp.features.dashboard.domain.model.UserDashboard
import com.sargedev.phoenixapp.features.dashboard.domain.repository.UserDashboardRepository
import javax.inject.Inject

class GetUserDashboardUseCase @Inject constructor(
    private val repository: UserDashboardRepository
) {
    suspend operator fun invoke(uid: String, gymId: String? = null): Result<UserDashboard> {
        return repository.getUserDashboard(uid, gymId)
    }
}
