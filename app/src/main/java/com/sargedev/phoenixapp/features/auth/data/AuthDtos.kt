package com.sargedev.phoenixapp.features.auth.data

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val status: String
)

data class TokensDto(
    val accessToken: String,
    val refreshToken: String
)

data class AuthResponseDto(
    val status: String,
    val responseCode: String,
    val message: String,
    val correlationId: String,
    val data: AuthDataDto
)

data class AuthDataDto(
    val isNewUser: Boolean,
    val timeOffsetMs: Long,
    val user: UserDto,
    val tokens: TokensDto
)
