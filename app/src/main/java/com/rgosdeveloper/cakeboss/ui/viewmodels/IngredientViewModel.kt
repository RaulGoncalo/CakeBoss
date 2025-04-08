package com.rgosdeveloper.cakeboss.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rgosdeveloper.cakeboss.data.repository.IngredientRepository
import com.rgosdeveloper.cakeboss.domain.common.ResultStateOperation
import com.rgosdeveloper.cakeboss.domain.models.Ingredient
import com.rgosdeveloper.cakeboss.domain.models.toIngredientEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    val repository: IngredientRepository
) : ViewModel() {

    private var _insertIngredient = MutableLiveData<ResultStateOperation<String>>()
    val insertIngredient: LiveData<ResultStateOperation<String>> get() = _insertIngredient

    private var _deleteIngredient = MutableLiveData<ResultStateOperation<String>>()
    val deleteIngredient: LiveData<ResultStateOperation<String>> get() = _deleteIngredient

    private var _ingredients = MutableLiveData<ResultStateOperation<List<Ingredient>>>()
    val ingredients: LiveData<ResultStateOperation<List<Ingredient>>> get() = _ingredients

    fun insertIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            val id = repository.insertIngredient(ingredient.toIngredientEntity())

            _insertIngredient.value = ResultStateOperation.Loading
            _insertIngredient.value = if (id is ResultStateOperation.Success) {
                ResultStateOperation.Success("Ingrediente inserido com sucesso")
            } else {
                ResultStateOperation.Error(Exception("Erro ao inserir ingrediente"))
            }
        }
    }

    fun deleteIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            val id = repository.deleteIngredient(ingredient.toIngredientEntity())

            _deleteIngredient.value = ResultStateOperation.Loading
            _deleteIngredient.value = if (id is ResultStateOperation.Success) {
                ResultStateOperation.Success("Ingrediente deletado com sucesso")
            } else {
                ResultStateOperation.Error(Exception("Erro ao inserir ingrediente"))
            }
        }
    }

    fun updateIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            repository.updateIngredient(ingredient.toIngredientEntity())
        }
    }

    fun readAllIngredients() {
        viewModelScope.launch {
            val ingredients = repository.realAllIngredients()

            _ingredients.value = ResultStateOperation.Loading
            _ingredients.value = if (ingredients is ResultStateOperation.Success) {
                ResultStateOperation.Success(ingredients.value.map {
                    Ingredient(
                        id = it.id,
                        name = it.name,
                        pricePackage = it.pricePackage,
                        packageContents = it.packageContents,
                        unitOfMeasurement = it.unitOfMeasurement
                    )
                })
            } else {
                ResultStateOperation.Error(Exception("Erro ao ler ingredientes"))
            }
        }
    }
}