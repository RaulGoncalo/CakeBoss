package com.rgosdeveloper.cakeboss.domain.models

data class Ingredient (
    val name: String,
    val pricePackage: Double,
    val packageContents: Double,
    val unitOfMeasurement: Measurement,
)