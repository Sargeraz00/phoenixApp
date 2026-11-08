package com.sargedev.phoenixapp.features.dashboard.domain.model

data class AccessQr(
    val qrContent: String,
    val parentUid: String,
    val expiresAt: String?,
    val expiresAtUnix: Long
)
