package com.sargedev.phoenixapp.features.auth.data.repository

import com.sargedev.phoenixapp.features.auth.data.datasource.AuthRemoteDataSource
import com.sargedev.phoenixapp.features.auth.data.mapper.toDomain
import com.sargedev.phoenixapp.features.auth.domain.repository.AuthRepository
import com.sargedev.phoenixapp.features.auth.domain.model.LoginResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun loginWithGoogle(idToken: String): LoginResult {
        val response = remoteDataSource.loginWithGoogle(idToken)
        return response.toDomain()
    }
}
