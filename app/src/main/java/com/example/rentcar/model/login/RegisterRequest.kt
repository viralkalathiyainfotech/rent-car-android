package com.example.rentcar.model.login

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("firstname")
    val firstname: String,

    @field:SerializedName("lastname")
    val lastname: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("licenceNo")
    val licenceNo: String,

    @field:SerializedName("password")
    val password: String
)