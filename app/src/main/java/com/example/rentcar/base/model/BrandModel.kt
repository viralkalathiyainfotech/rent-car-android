package com.example.rentcar.base.model

data class BrandModel(
    val initials: String,
    val category: String,
    val brandName: String,
    val tagline: String,
    val carsCount: Int,
    val accentColor: String,
    val rating: Float = 5.0f
)