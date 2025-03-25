package com.rgosdeveloper.cakeboss.domain.models

data class Recipe(
    val name: String,
    val imageUrl: String?,
    val duration: Int,
    val totalCost: Double,
    val portions: Int,
    val costPerPortion: Double,
    val ingredients: List<Ingredient>
)
