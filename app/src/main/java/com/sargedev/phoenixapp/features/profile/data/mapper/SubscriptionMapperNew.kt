package com.sargedev.phoenixapp.features.profile.data.mapper

import com.sargedev.phoenixapp.features.profile.data.dto.SubscriptionResponseDto
import com.sargedev.phoenixapp.features.profile.domain.model.UserSubscription

fun SubscriptionResponseDto.toDomain(): UserSubscription {
    val d = data
    return UserSubscription(
        gymId = d.gymId,
        planName = d.planName,
        price = d.price,
        currency = d.currency,
        nextPaymentAt = d.nextPaymentAt,
        status = d.status,
        paymentStatus = d.paymentStatus,
        outstandingAmount = d.outstandingAmount,
        daysRemaining = d.daysRemaining
    )
}
