package com.sargedev.phoenixapp.features.dashboard.domain.usecase

import com.sargedev.phoenixapp.features.dashboard.domain.model.AccessQr
import com.sargedev.phoenixapp.features.dashboard.domain.repository.UserDashboardRepository
import javax.inject.Inject

class GetAccessQrUseCase @Inject constructor(
    private val repository: UserDashboardRepository
) {
    suspend operator fun invoke(uid: String, gymId: String? = null): Result<AccessQr> {
        return repository.getAccessQr(uid, gymId)
    }
}
