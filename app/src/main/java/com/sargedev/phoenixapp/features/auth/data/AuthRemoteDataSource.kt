package com.sargedev.phoenixapp.features.auth.data

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class AuthRemoteDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 10000
            requestTimeoutMillis = 10000
        }
    }

    // Using 127.0.0.1 combined with adb reverse tcp:8080 tcp:8080 is 100% reliable on Android emulators
    private val baseUrl = "http://127.0.0.1:8080/api/v1"

    suspend fun loginWithGoogle(idToken: String): GoogleLoginResponseDto {
        Log.d("AuthDebug", "Sending POST to $baseUrl/auth/google/login")
        val response: GoogleLoginResponseDto = client.post("$baseUrl/auth/google/login") {
            contentType(ContentType.Application.Json)
            setBody(GoogleLoginRequestDto(idToken))
        }.body()
        Log.d("AuthDebug", "Received backend response: isFirstLogin=${response.data.isFirstLogin}")
        return response
    }
}
