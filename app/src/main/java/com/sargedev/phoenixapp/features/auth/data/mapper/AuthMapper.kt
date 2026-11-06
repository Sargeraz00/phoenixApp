package com.sargedev.phoenixapp.features.auth.data.mapper

import com.sargedev.phoenixapp.features.auth.data.dto.GoogleLoginResponseDto
import com.sargedev.phoenixapp.features.auth.domain.model.LoginResult

fun GoogleLoginResponseDto.toDomain(): LoginResult {
    val data = this.data
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
