package com.sargedev.phoenixapp.features.profile.domain.model

data class UserSubscription(
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
