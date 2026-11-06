package com.sargedev.phoenixapp.features.auth.data

import android.util.Log
import com.sargedev.phoenixapp.BuildConfig
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val client: HttpClient
) {
    private val baseUrl = BuildConfig.BASE_URL

    suspend fun loginWithGoogle(idToken: String): GoogleLoginResponseDto {
        Log.d("AuthDebug", "Sending POST to $baseUrl/auth/google/login")
        val response: GoogleLoginResponseDto = client.post("$baseUrl/auth/google/login") {
            contentType(ContentType.Application.Json)
            setBody(GoogleLoginRequestDto(idToken))
        }.body()
        Log.d("AuthDebug", "Received backend response: isFirstLogin=${response.data.isFirstLogin}, uid=${response.data.uid}")
        return response
    }
}
