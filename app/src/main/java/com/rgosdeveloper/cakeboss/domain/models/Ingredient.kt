package com.rgosdeveloper.cakeboss.domain.models

import android.os.Parcelable
import com.rgosdeveloper.cakeboss.data.entitys.IngredientEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient (
    val id: Int?,
    val name: String,
    val pricePackage: Double,
    val packageContents: Double,
    val unitOfMeasurement: Measurement,
): Parcelable

fun Ingredient.toIngredientEntity(): IngredientEntity {
    return IngredientEntity(
        id = this.id ?: 0,
        name = this.name,
        pricePackage = this.pricePackage,
        packageContents = this.packageContents,
        unitOfMeasurement = this.unitOfMeasurement,
    )
}