package com.example.rentcar.apiService

import com.example.rentcar.model.CreateUSerResponse
import com.example.rentcar.model.login.ForgotPasswordRequest
import com.example.rentcar.model.login.ForgotPasswordResponse
import com.example.rentcar.model.login.LoginRequest
import com.example.rentcar.model.login.RegisterRequest
import com.example.rentcar.model.login.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/users/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<RegisterUserResponse>

    @POST("api/users/register")   // ← adjust path to match your backend
    suspend fun registerUser(
        @Body request: RegisterRequest
    ): Response<CreateUSerResponse>

    @POST("api/users/forgotpassword")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>
}