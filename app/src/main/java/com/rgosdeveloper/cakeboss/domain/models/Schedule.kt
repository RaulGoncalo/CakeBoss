package com.rgosdeveloper.cakeboss.domain.models

import java.time.LocalDate

data class Schedule (
    val client: Client,
    val recipe: Recipe,
    val date: LocalDate,
    var status: Status,
    val quantity: Int
){
    val totalPrice: Double
        get() = recipe.costPerPortion * quantity
}