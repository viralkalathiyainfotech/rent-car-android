package com.example.rentcar.model

import com.google.gson.annotations.SerializedName

data class CreateUSerResponse(

    @field:SerializedName("firstname")
    val firstname: String,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("licenceNo")
    val licenceNo: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("lastname")
    val lastname: String,

    @field:SerializedName("token")
    val token: String
)
