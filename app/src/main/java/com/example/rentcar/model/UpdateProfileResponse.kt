package com.example.rentcar.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

    @field:SerializedName("firstname")
    val firstname: String,

    @field:SerializedName("img")
    val img: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("licenceNo")
    val licenceNo: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("phoneNo")
    val phoneNo: String,

    @field:SerializedName("lastname")
    val lastname: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("token")
    val token: String
)
