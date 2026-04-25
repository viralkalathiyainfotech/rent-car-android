package com.example.rentcar.model.login

data class LoginResponse(
    val _id: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val licenceNo: String,
    val role: String,
    val token: String
)