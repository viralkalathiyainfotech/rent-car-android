package com.example.rentcar.repository

import android.util.Log
import com.example.rentcar.apiService.ApiService
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.model.MyBookingResponseItem
import com.example.rentcar.model.UpdateProfileResponse
import com.example.rentcar.model.home.BrandResponseModel
import com.example.rentcar.model.home.CarResponseModel
import com.example.rentcar.model.login.ChangePasswordResponse
import com.example.rentcar.model.login.ForgotPasswordResponse
import com.example.rentcar.model.login.LoginResponse
import com.example.rentcar.model.login.RegisterResponseModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class UserRepository @Inject constructor( private val apiService: ApiService){
    suspend fun loginUser(email: String, password: String): LoginResponse {
        val credentials = mapOf(
            "email" to email,
            "password" to password
        )
        return apiService.loginUser(credentials)
    }

    suspend fun registerUser(
        firstname: String,
        lastname: String,
        email: String,
        phone: String,
        licenceNo: String,
        password: String
    ): RegisterResponseModel {
        val requestBody = mapOf(
            "firstname"  to firstname,
            "lastname"   to lastname,
            "email"      to email,
            "phone"      to phone,
            "licenceNo"  to licenceNo,
            "password"   to password
        )
        return apiService.registerUser(requestBody)
    }

    suspend fun getAllCars(): UiState<CarResponseModel> {
        return try {
            val response = apiService.getAllCars()
            if (response.isSuccessful && response.body() != null) {
                UiState.Success(response.body()!!)
            } else {
                UiState.Error(response.message() ?: "Something went wrong")
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Network error")
        }
    }

    suspend fun getAllBrands(): UiState<BrandResponseModel> {
        return try {
            val response = apiService.getAllBrands()
            if (response.isSuccessful && response.body() != null) {
                UiState.Success(response.body()!!)
            } else {
                UiState.Error(response.message() ?: "Something went wrong")
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Network error")
        }
    }
    suspend fun forgotPassword(email: String): ForgotPasswordResponse {
        val request = mapOf("email" to email)
        return apiService.forgotPassword(request)
    }
    suspend fun updateProfile(
        token: String,
        firstName: String,
        lastName: String,
        phone: String,
        imgPart: MultipartBody.Part?
    ): UiState<UpdateProfileResponse> {
        return try {
            val firstNameBody = firstName.toRequestBody("text/plain".toMediaTypeOrNull())
            val lastNameBody = lastName.toRequestBody("text/plain".toMediaTypeOrNull())
            val phoneBody = phone.toRequestBody("text/plain".toMediaTypeOrNull())

            Log.d("Edit Profile : ", "$firstNameBody")
            Log.d("Edit Profile : ", "$lastNameBody")
            Log.d("Edit Profile : ", "$phoneBody")
            Log.d("Edit Profile : ", "$imgPart")

            val response = apiService.updateProfile(
                token = "Bearer $token",
                firstName = firstNameBody,
                lastName = lastNameBody,
                phone = phoneBody,
                img = imgPart
            )
            UiState.Success(response)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Something went wrong")
        }
    }

    suspend fun changePassword(
        token: String,
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ChangePasswordResponse {

        val request = mapOf(
            "token" to token,
            "oldPassword" to oldPassword,
            "newPassword" to newPassword,
            "confirmPassword" to confirmPassword
        )
        return apiService.changePassword("Bearer $token" , request)
    }

    suspend fun getMyBookings(token: String): UiState<List<MyBookingResponseItem>> {
        return try {
            val response = apiService.getMyBookings("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                UiState.Success(response.body()!!)
            } else {
                UiState.Error(response.message() ?: "Something went wrong")
            }
        } catch (e: Exception) {
            UiState.Error(e.message ?: "Network error")
        }
    }
}