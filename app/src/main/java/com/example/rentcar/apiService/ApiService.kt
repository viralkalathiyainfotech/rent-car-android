package com.example.rentcar.apiService

import com.example.rentcar.model.home.BrandResponseModel
import com.example.rentcar.model.home.CarResponseModel
import com.example.rentcar.model.login.LoginResponse
import com.example.rentcar.model.login.RegisterResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("api/users/login")
    suspend fun loginUser(@Body credentials: Map<String, String>): LoginResponse

    @POST("api/users/register")
    suspend fun registerUser(@Body requestBody: Map<String, String>): RegisterResponseModel

    @GET("api/cars")
    suspend fun getAllCars(): Response<CarResponseModel>

    @GET("api/brands")
    suspend fun getAllBrands(): Response<BrandResponseModel>
}