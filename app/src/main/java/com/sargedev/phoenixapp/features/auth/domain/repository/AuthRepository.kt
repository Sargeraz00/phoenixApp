package com.sargedev.phoenixapp.features.auth.domain.repository

import com.sargedev.phoenixapp.features.auth.domain.model.LoginResult

interface AuthRepository {
    suspend fun loginWithGoogle(idToken: String): LoginResult
}
