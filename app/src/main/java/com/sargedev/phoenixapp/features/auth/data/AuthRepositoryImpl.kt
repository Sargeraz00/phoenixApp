package com.sargedev.phoenixapp.features.auth.data

import com.sargedev.phoenixapp.features.auth.domain.AuthRepository
import com.sargedev.phoenixapp.features.auth.domain.AuthResult
import com.sargedev.phoenixapp.features.auth.domain.User

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource = AuthRemoteDataSource()
) : AuthRepository {
    override suspend fun loginWithEmail(email: String, pass: String): AuthResult {
        val response = remoteDataSource.loginWithEmail(email, pass)
        val userDto = response.data.user
        val user = User(
            id = userDto.id,
            name = userDto.name,
            email = userDto.email,
            role = userDto.role,
            status = userDto.status
        )
        return AuthResult(
            isNewUser = response.data.isNewUser,
            user = user,
            accessToken = response.data.tokens.accessToken,
            refreshToken = response.data.tokens.refreshToken
        )
    }

    override suspend fun loginWithGoogle(idToken: String): AuthResult {
        val response = remoteDataSource.loginWithGoogle(idToken)
        val userDto = response.data.user
        val user = User(
            id = userDto.id,
            name = userDto.name,
            email = userDto.email,
            role = userDto.role,
            status = userDto.status
        )
        return AuthResult(
            isNewUser = response.data.isNewUser,
            user = user,
            accessToken = response.data.tokens.accessToken,
            refreshToken = response.data.tokens.refreshToken
        )
    }
}
