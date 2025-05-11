package com.rgosdeveloper.cakeboss.di

import android.content.Context
import com.rgosdeveloper.cakeboss.data.daos.IngredientDAO
import com.rgosdeveloper.cakeboss.data.database.CustomDatabase
import com.rgosdeveloper.cakeboss.domain.repository.IngredientRepository
import com.rgosdeveloper.cakeboss.data.repository.IngredientRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCustomDatabase(@ApplicationContext context: Context) : CustomDatabase {
        return CustomDatabase.getInstance(context)
    }

    @Provides
    fun provideIngredientDao(database: CustomDatabase) : IngredientDAO {
        return database.ingredientDao
    }

    @Provides
    fun provideIngredientRepository(dao: IngredientDAO) : IngredientRepository {
        return IngredientRepositoryImpl(dao)
    }
}