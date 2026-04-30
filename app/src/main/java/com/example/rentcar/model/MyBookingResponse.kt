package com.example.rentcar.model

import com.google.gson.annotations.SerializedName

data class MyBookingResponse(

    @field:SerializedName("MyBookingResponse")
    val myBookingResponse: List<MyBookingResponseItem>
)

data class Specs(

    @field:SerializedName("acceleration")
    val acceleration: String,

    @field:SerializedName("transmission")
    val transmission: String,

    @field:SerializedName("fuel")
    val fuel: String,

    @field:SerializedName("seating")
    val seating: String
)

data class MyBookingResponseItem(

    @field:SerializedName("paymentPercentage")
    val paymentPercentage: Int,

    @field:SerializedName("returnTime")
    val returnTime: String,

    @field:SerializedName("totalAmount")
    val totalAmount: Int,

    @field:SerializedName("proofOfIdentity")
    val proofOfIdentity: Boolean,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("pickupTime")
    val pickupTime: String,

    @field:SerializedName("returnDate")
    val returnDate: String,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String,

    @field:SerializedName("car")
    val car: Car,

    @field:SerializedName("rentalType")
    val rentalType: String,

    @field:SerializedName("__v")
    val v: Int,

    @field:SerializedName("pickupDate")
    val pickupDate: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("user")
    val user: String,

    @field:SerializedName("paymentStatus")
    val paymentStatus: String,

    @field:SerializedName("stripeSessionId")
    val stripeSessionId: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)

data class Car(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("isAvailable")
    val isAvailable: Boolean,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("pricePerHour")
    val pricePerHour: Int,

    @field:SerializedName("pricePerDay")
    val pricePerDay: Int,

    @field:SerializedName("specs")
    val specs: Specs,

    @field:SerializedName("features")
    val features: List<String>,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("__v")
    val v: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("_id")
    val id: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("brand")
    val brand: String,

    @field:SerializedName("thumbs")
    val thumbs: List<String>,

    @field:SerializedName("updatedAt")
    val updatedAt: String
)
