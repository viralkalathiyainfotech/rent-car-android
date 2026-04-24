package com.example.rentcar.apiService

import com.example.rentcar.model.login.LoginRequest
import com.example.rentcar.model.login.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/users/login")
    suspend fun loginUser(
        @Body request: LoginRequest
    ): Response<RegisterUserResponse>
}