package com.sargedev.phoenixapp.features.dashboard.data.datasource

import android.util.Log
import com.sargedev.phoenixapp.BuildConfig
import com.sargedev.phoenixapp.features.dashboard.data.dto.AccessQrResponseDto
import com.sargedev.phoenixapp.features.dashboard.data.dto.DashboardResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class UserDashboardRemoteDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    private val baseUrl = BuildConfig.BASE_URL

    suspend fun getDashboardUser(uid: String, gymId: String? = null): DashboardResponseDto {
        Log.d("AuthDebug", "Fetching user dashboard GET $baseUrl/users/me/dashboard")
        return httpClient.get("$baseUrl/users/me/dashboard") {
            contentType(ContentType.Application.Json)
            header("X-User-UID", uid)
            if (!gymId.isNullOrEmpty()) {
                header("X-Gym-ID", gymId)
            }
        }.body()
    }

    suspend fun getAccessQr(uid: String, gymId: String? = null): AccessQrResponseDto {
        Log.d("AuthDebug", "Fetching user access QR GET $baseUrl/users/me/access-qr")
        return httpClient.get("$baseUrl/users/me/access-qr") {
            contentType(ContentType.Application.Json)
            header("X-User-UID", uid)
            if (!gymId.isNullOrEmpty()) {
                header("X-Gym-ID", gymId)
            }
        }.body()
    }
}
