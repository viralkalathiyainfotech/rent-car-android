package com.example.rentcar.model.login

import com.google.gson.annotations.SerializedName

data class RegisterResponseModel(
    @SerializedName("_id")        val _id: String,
    @SerializedName("email")      val email: String,
    @SerializedName("firstName")  val firstname: String,
    @SerializedName("lastName")   val lastname: String,
    @SerializedName("licenceNo")  val licenceNo: String,
    @SerializedName("role")       val role: String,
    @SerializedName("token")      val token: String
)