package com.example.rentcar.model

import com.google.gson.annotations.SerializedName

data class CreateRatingResponse(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("ratings")
    val ratings: List<RatingsItem>,

    @field:SerializedName("__v")
    val v: Int,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class RatingsItem(

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("rating")
    val rating: Int,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("bookingId")
    val bookingId: String,

    @field:SerializedName("carId")
    val carId: String
)
