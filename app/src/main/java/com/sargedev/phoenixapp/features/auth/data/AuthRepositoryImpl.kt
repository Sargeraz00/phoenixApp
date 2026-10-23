package com.sargedev.phoenixapp.features.auth.data

import com.sargedev.phoenixapp.features.auth.domain.AuthRepository
import com.sargedev.phoenixapp.features.auth.domain.LoginResult

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource = AuthRemoteDataSource()
) : AuthRepository {
    override suspend fun loginWithGoogle(idToken: String): LoginResult {
        val response = remoteDataSource.loginWithGoogle(idToken)
        val data = response.data
        return LoginResult(
            uid = data.uid,
            email = data.email,
            name = data.name,
            role = data.role,
            status = data.status,
            isFirstLogin = data.isFirstLogin,
            photoUrl = data.photoUrl
        )
    }
}
