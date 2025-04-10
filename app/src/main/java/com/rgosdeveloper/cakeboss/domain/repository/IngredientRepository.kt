package com.rgosdeveloper.cakeboss.domain.repository

import com.rgosdeveloper.cakeboss.data.entitys.IngredientEntity
import com.rgosdeveloper.cakeboss.domain.common.ResultStateOperation

interface IngredientRepository {
    suspend fun insertIngredient(ingredient: IngredientEntity): ResultStateOperation<Long>
    suspend fun deleteIngredient(ingredient: IngredientEntity): ResultStateOperation<Int>
    suspend fun updateIngredient(ingredient: IngredientEntity): ResultStateOperation<Int>
    suspend fun realAllIngredients(): ResultStateOperation<List<IngredientEntity>>
}