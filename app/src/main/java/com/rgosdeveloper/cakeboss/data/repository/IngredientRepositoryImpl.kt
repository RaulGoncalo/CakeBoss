package com.rgosdeveloper.cakeboss.data.repository

import com.rgosdeveloper.cakeboss.data.daos.IngredientDAO
import com.rgosdeveloper.cakeboss.data.entitys.IngredientEntity
import com.rgosdeveloper.cakeboss.domain.common.ResultStateOperation
import com.rgosdeveloper.cakeboss.domain.repository.IngredientRepository
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    val ingredientDAO: IngredientDAO
) :
    IngredientRepository {
     override suspend fun insertIngredient(ingredient: IngredientEntity): ResultStateOperation<Long> {
        try {
            val id = ingredientDAO.insert(ingredient)
            if(id > 0L){
                return ResultStateOperation.Success(id)
            }else{
                return ResultStateOperation.Error(Exception("Erro ao inserir ingrediente"))
            }
        }catch (errorInsertRoom: Exception){
            return ResultStateOperation.Error(errorInsertRoom)
        }
    }

    override suspend fun deleteIngredient(ingredient: IngredientEntity): ResultStateOperation<Int> {
        try {
            val affectedLines = ingredientDAO.delete(ingredient)
            if(affectedLines > 0L){
                return ResultStateOperation.Success(affectedLines)
            }else{
                return ResultStateOperation.Error(Exception("Erro ao deletar ingrediente"))
            }
        }catch (errorDeleteRoom: Exception){
            return ResultStateOperation.Error(errorDeleteRoom)
        }
    }

    override suspend fun updateIngredient(ingredient: IngredientEntity): ResultStateOperation<Int> {
        try {
            val affectedLines = ingredientDAO.update(ingredient)
            if(affectedLines > 0L){
                return ResultStateOperation.Success(affectedLines)
            }else{
                return ResultStateOperation.Error(Exception("Erro ao atualizar ingrediente"))
            }
        }catch (errorDeleteRoom: Exception){
            return ResultStateOperation.Error(errorDeleteRoom)
        }
    }

    override suspend fun realAllIngredients(): ResultStateOperation<List<IngredientEntity>> {
        try {
            val ingredients = ingredientDAO.readAll()
            if(ingredients != null){
                return ResultStateOperation.Success(ingredients)
            }else{
                return ResultStateOperation.Error(Exception("Erro ao ler os ingredientes"))
            }
        }catch (errorReadRoom: Exception){
            return ResultStateOperation.Error(errorReadRoom)
        }
    }

}