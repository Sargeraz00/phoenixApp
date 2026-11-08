package com.sargedev.phoenixapp.features.dashboard.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccessQrResponseDto(
    val status: String,
    val responseCode: String? = null,
    val message: String? = null,
    val correlationId: String? = null,
    val data: AccessQrDataDto
)

@Serializable
data class AccessQrDataDto(
    val qrContent: String,
    val parentUid: String,
    val expiresAt: String? = null,
    val expiresAtUnix: Long
)
