package com.example.rentcar.apiService

import com.example.rentcar.model.CreateRatingResponse
import com.example.rentcar.model.MyBookingResponseItem
import com.example.rentcar.model.UpdateProfileResponse
import com.example.rentcar.model.home.BrandResponseModel
import com.example.rentcar.model.home.CarResponseModel
import com.example.rentcar.model.login.ChangePasswordResponse
import com.example.rentcar.model.login.ForgotPasswordResponse
import com.example.rentcar.model.login.LoginResponse
import com.example.rentcar.model.login.RegisterResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
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

    @POST("api/users/forgotpassword")
    suspend fun forgotPassword(@Body request: Map<String, String>): ForgotPasswordResponse

    @Multipart
    @PUT("api/users/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Part("firstname") firstName: RequestBody,
        @Part("lastname") lastName: RequestBody,
        @Part("phoneNo") phone: RequestBody,
        @Part("location") location: RequestBody,
        @Part img: MultipartBody.Part?
    ): UpdateProfileResponse

    @PUT("api/users/changepassword")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body requestBody: Map<String, String>
    ): ChangePasswordResponse

    @GET("api/bookings/mybookings")
    suspend fun getMyBookings(
        @Header("Authorization") token: String
    ): Response<List<MyBookingResponseItem>>

    @RequiresAuth
    @POST("api/ratings")
    suspend fun createRating(
        @Body request: Map<String, @JvmSuppressWildcards Any>
    ): Response<CreateRatingResponse>

}