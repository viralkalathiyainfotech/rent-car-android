package com.example.rentcar.model.home

class CarResponseModel : ArrayList<CarResponseModel.CarResponseModelItem>(){
    data class CarResponseModelItem(
        val __v: Int,
        val _id: String,
        val brand: Brand,
        val category: String,
        val createdAt: String,
        val description: String,
        val features: List<String>,
        val image: String,
        val isAvailable: Boolean,
        val name: String,
        val pricePerDay: Int,
        val pricePerHour: Int,
        val specs: Specs,
        val thumbs: List<String>,
        val updatedAt: String
    ) {
        data class Brand(
            val _id: String,
            val logo: String,
            val name: String,
            val subtitle: String
        )
    
        data class Specs(
            val acceleration: String,
            val fuel: String,
            val seating: String,
            val transmission: String
        )
    }
}