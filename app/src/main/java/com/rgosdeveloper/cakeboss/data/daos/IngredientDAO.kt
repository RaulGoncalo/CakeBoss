package com.rgosdeveloper.cakeboss.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rgosdeveloper.cakeboss.data.entitys.IngredientEntity
import com.rgosdeveloper.cakeboss.utils.Constants

@Dao
interface IngredientDAO {
    @Insert
    suspend fun insert(ingredient: IngredientEntity): Long

    @Delete
    suspend fun delete(ingredient: IngredientEntity): Int

    @Update
    suspend fun update(ingredient: IngredientEntity): Int

    @Query("SELECT * FROM ${Constants.Database.TABLE_NAME_INGREDIENTS}")
    suspend fun readAll(): List<IngredientEntity>

    @Query("SELECT * FROM ${Constants.Database.TABLE_NAME_INGREDIENTS} WHERE ${Constants.Database.COLUMN_INFO_ID_INGREDIENT} = :id")
    suspend fun readIngredient(id: Int): IngredientEntity
}