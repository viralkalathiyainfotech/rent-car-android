package com.example.rentcar.model.home

class BrandResponseModel : ArrayList<BrandResponseModel.BrandResponseModelItem>(){
    data class BrandResponseModelItem(
        val __v: Int,
        val _id: String,
        val accent: String,
        val createdAt: String,
        val logo: String,
        val name: String,
        val subtitle: String,
        val tag: String,
        val tagline: String,
        val updatedAt: String
    )
}