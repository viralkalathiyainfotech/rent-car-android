package com.example.rentcar.repository

import com.example.rentcar.apiService.ApiService
import com.example.rentcar.base.utils.UiState
import com.example.rentcar.model.home.BrandResponseModel
import com.example.rentcar.model.home.CarResponseModel
import com.example.rentcar.model.login.LoginResponse
import com.example.rentcar.model.login.RegisterUserResponse
import dagger.hilt.android.AndroidEntryPoint
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
    ): RegisterUserResponse {
        val requestBody = mapOf(
            "firstName" to firstname,
            "lastName" to lastname,
            "email" to email,
            "phoneNumber" to phone,
            "licenceNo" to licenceNo,
            "password" to password
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
}