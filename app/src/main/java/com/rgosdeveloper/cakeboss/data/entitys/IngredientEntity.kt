package com.rgosdeveloper.cakeboss.data.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rgosdeveloper.cakeboss.domain.models.Measurement
import com.rgosdeveloper.cakeboss.utils.Constants

@Entity(tableName = Constants.Database.TABLE_NAME_INGREDIENTS)
data class IngredientEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.Database.COLUMN_INFO_ID_INGREDIENT)
    val id: Int,
    val name: String,
    val pricePackage: Double,
    val packageContents: Double,
    val unitOfMeasurement: Measurement,
)