package com.example.rentcar.model.login

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)
