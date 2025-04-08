package com.rgosdeveloper.cakeboss.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rgosdeveloper.cakeboss.data.daos.IngredientDAO
import com.rgosdeveloper.cakeboss.data.entitys.IngredientEntity
import com.rgosdeveloper.cakeboss.utils.Constants


@Database(
    entities = [IngredientEntity::class],
    version = Constants.Database.DATABASE_VERSION,
)
abstract class CustomDatabase: RoomDatabase() {
    //Configurar os DAOS
    abstract val ingredientDao: IngredientDAO

    //Configurar a instancia do banco de dados
    companion object{
        fun getInstance(context: Context) : CustomDatabase{
            return Room.databaseBuilder(
                context,
                CustomDatabase::class.java,
                Constants.Database.DATABASE_NAME
            ).build()
        }
    }
}