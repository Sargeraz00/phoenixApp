package com.sargedev.phoenixapp.features.profile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionResponseDto(
    val status: String,
    val responseCode: String,
    val message: String,
    val correlationId: String,
    val data: SubscriptionDataDto
)

@Serializable
data class SubscriptionDataDto(
    val gymId: String,
    val planName: String,
    val price: Double,
    val currency: String,
    val nextPaymentAt: String,
    val status: String,
    val paymentStatus: String,
    val outstandingAmount: Double,
    val daysRemaining: Int
)
