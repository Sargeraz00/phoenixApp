package com.sargedev.phoenixapp.features.auth.domain.usecase

import com.sargedev.phoenixapp.features.auth.domain.model.LoginResult
import com.sargedev.phoenixapp.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginWithGoogleUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): LoginResult {
        return authRepository.loginWithGoogle(idToken)
    }
}
