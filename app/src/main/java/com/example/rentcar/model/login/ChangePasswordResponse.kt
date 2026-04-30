package com.example.rentcar.model.login

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String
)
