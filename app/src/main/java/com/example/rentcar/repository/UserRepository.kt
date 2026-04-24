package com.example.rentcar.repository

import com.example.rentcar.apiService.RetrofitClient
import com.example.rentcar.base.utils.NetworkResult
import com.example.rentcar.model.login.LoginRequest
import com.example.rentcar.model.login.RegisterUserResponse
import org.json.JSONObject

class UserRepository {

    private val api = RetrofitClient.apiService

    suspend fun loginUser(
        email: String,
        password: String
    ): NetworkResult<RegisterUserResponse> {
        return try {
            val response = api.loginUser(LoginRequest(email, password))

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    NetworkResult.Success(body)
                } else {
                    NetworkResult.Error("Empty response from server")
                }
            } else {
                // ── Parse error body message from server ──
                val errorMessage = try {
                    val errorJson = response.errorBody()?.string()
                    if (!errorJson.isNullOrBlank()) {
                        val json = JSONObject(errorJson)
                        when {
                            json.has("message") -> json.getString("message")
                            json.has("error") -> json.getString("error")
                            else -> "Login failed (${response.code()})"
                        }
                    } else {
                        "Login failed (${response.code()})"
                    }
                } catch (e: Exception) {
                    "Login failed (${response.code()})"
                }
                NetworkResult.Error(errorMessage)
            }

        } catch (e: java.net.UnknownHostException) {
            NetworkResult.Error("No internet connection")
        } catch (e: java.net.SocketTimeoutException) {
            NetworkResult.Error("Connection timed out. Please try again")
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Something went wrong")
        }
    }
}