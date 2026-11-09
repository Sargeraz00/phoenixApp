package com.sargedev.phoenixapp.features.profile.data.datasource

import android.util.Log
import com.sargedev.phoenixapp.BuildConfig
import com.sargedev.phoenixapp.features.profile.data.dto.MemberResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class MemberDataSource @Inject constructor(
    private val httpClient: HttpClient
) {
    private val baseUrl = BuildConfig.BASE_URL

    suspend fun getMembersList(uid: String, gymId: String): MemberResponseDto {
        Log.d("AuthDebug", "Fetching members list GET $baseUrl/members/list")
        return httpClient.get("$baseUrl/members/list") {
            contentType(ContentType.Application.Json)
            header("X-User-UID", uid)
            header("X-Gym-ID", gymId)
        }.body()
    }
}
