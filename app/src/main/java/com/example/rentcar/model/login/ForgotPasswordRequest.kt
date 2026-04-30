package com.example.rentcar.model.login

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @field:SerializedName("email")
    val email: String
)