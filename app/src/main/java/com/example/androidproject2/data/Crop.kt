package com.example.androidproject2.data

data class Crop(
    val cropId: String,
    val name: String,
    val type: String,
    val quantity: Int,
    val location: String,
    val farmerId: String,
    val farmerName: String,
    val cropImage:Int,
    val cropDescription: String
)
