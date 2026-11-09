package com.sargedev.phoenixapp.features.profile.data.datasource

import android.util.Log
import com.sargedev.phoenixapp.BuildConfig
import com.sargedev.phoenixapp.features.profile.data.dto.SubscriptionResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class SubscriptionDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    private val baseUrl = BuildConfig.BASE_URL

    suspend fun getCurrentSubscription(uid: String, gymId: String): SubscriptionResponseDto {
        Log.d("AuthDebug", "Fetching current subscription GET $baseUrl/subscriptions/current")
        return httpClient.get("$baseUrl/subscriptions/current") {
            contentType(ContentType.Application.Json)
            header("X-User-UID", uid)
            header("X-Gym-ID", gymId)
        }.body()
    }
}
