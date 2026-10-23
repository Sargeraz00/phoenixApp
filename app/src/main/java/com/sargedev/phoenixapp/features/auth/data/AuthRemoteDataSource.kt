package com.sargedev.phoenixapp.features.auth.data

import kotlinx.coroutines.delay

class AuthRemoteDataSource {
    suspend fun loginWithEmail(email: String, pass: String): AuthResponseDto {
        delay(800)
        val isNew = email.contains("new", ignoreCase = true)
        val userId = if (isNew) "usr_123456789" else "usr_987654321"
        val name = if (isNew) "Carlos Ramírez" else "Diego Ramírez"
        val status = if (isNew) "PENDING_PROFILE" else "ACTIVE"
        val responseCode = if (isNew) "PHX-2010" else "PHX-2000"
        val message = if (isNew) "Usuario creado y autenticado." else "Login exitoso."

        return AuthResponseDto(
            status = "success",
            responseCode = responseCode,
            message = message,
            correlationId = "1304d405-e68f-45b8-90ad-fdbf72fe6b58",
            data = AuthDataDto(
                isNewUser = isNew,
                timeOffsetMs = 1200,
                user = UserDto(
                    id = userId,
                    name = name,
                    email = email,
                    role = "ALUMNO",
                    status = status
                ),
                tokens = TokensDto(
                    accessToken = "ya29.mock_access_token_${System.currentTimeMillis()}",
                    refreshToken = "1//mock_refresh_token"
                )
            )
        )
    }

    suspend fun loginWithGoogle(idToken: String): AuthResponseDto {
        delay(900)
        return AuthResponseDto(
            status = "success",
            responseCode = "PHX-2000",
            message = "Login exitoso con Google.",
            correlationId = "a20d372e-57c5-4fd2-8776-b10f7ad4b298",
            data = AuthDataDto(
                isNewUser = false,
                timeOffsetMs = 900,
                user = UserDto(
                    id = "usr_987654321",
                    name = "Diego Ramírez",
                    email = "diego.ramirez@example.com",
                    role = "ALUMNO",
                    status = "ACTIVE"
                ),
                tokens = TokensDto(
                    accessToken = "ya29.google_access_token",
                    refreshToken = "1//google_refresh_token"
                )
            )
        )
    }
}
