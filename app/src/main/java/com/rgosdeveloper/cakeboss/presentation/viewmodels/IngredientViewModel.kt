package com.rgosdeveloper.cakeboss.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rgosdeveloper.cakeboss.domain.repository.IngredientRepository
import com.rgosdeveloper.cakeboss.domain.common.ResultStateOperation
import com.rgosdeveloper.cakeboss.domain.models.Ingredient
import com.rgosdeveloper.cakeboss.domain.models.Measurement
import com.rgosdeveloper.cakeboss.domain.models.toIngredientEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    val repository: IngredientRepository
) : ViewModel() {

    private var _ingredients = MutableLiveData<ResultStateOperation<List<Ingredient>>>()
    val ingredients: LiveData<ResultStateOperation<List<Ingredient>>> get() = _ingredients

    private var _ingredient = MutableLiveData<ResultStateOperation<Ingredient>?>()
    val ingredient: LiveData<ResultStateOperation<Ingredient>?> get() = _ingredient

    init {
        readAllIngredients()
    }

    fun insertIngredient(
        name: String,
        pricePackage: String,
        packageContents: String,
        unitOfMeasurement: Measurement
    ) {
        val ingredient = toIngredient(
            name = name,
            pricePackage = pricePackage,
            packageContents = packageContents,
            unitOfMeasurement = unitOfMeasurement
        )

        if (!validateIngredient(ingredient)) {
            _ingredient.value = ResultStateOperation.Error(Exception("Valide os campos"))
            return
        }

        viewModelScope.launch {
            val id = repository.insertIngredient(ingredient.toIngredientEntity())

            if (id is ResultStateOperation.Success) {
                readAllIngredients()
            } else {
                _ingredient.value =
                    ResultStateOperation.Error(Exception("Erro ao inserir ingrediente"))
            }
        }
    }


    fun deleteIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            if (ingredient == null) {
                _ingredient.value = ResultStateOperation.Error(Exception("Erro ao ler ingrediente"))
            } else {
                val id = repository.deleteIngredient(ingredient.toIngredientEntity())

                if (id is ResultStateOperation.Success) {
                    readAllIngredients()
                } else {
                    _ingredient.value =
                        ResultStateOperation.Error(Exception("Erro ao deletar ingrediente"))
                }
            }
        }
    }

    fun updateIngredient(
        id: Int,
        name: String,
        pricePackage: String,
        packageContents: String,
        unitOfMeasurement: Measurement
    ) {
        val ingredient = toIngredient(id, name, pricePackage, packageContents, unitOfMeasurement)

        viewModelScope.launch {
            repository.updateIngredient(ingredient.toIngredientEntity())
            readAllIngredients()
        }
    }

    fun readAllIngredients() {
        viewModelScope.launch {
            val ingredients = repository.readAllIngredients()

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

    fun readIngredientById(id: Int) {
        viewModelScope.launch {
            val result = repository.readIngredientById(id)

            _ingredient.value = ResultStateOperation.Loading
            _ingredient.value = if (result is ResultStateOperation.Success) {
                ResultStateOperation.Success(
                    Ingredient(
                        id = result.value.id,
                        name = result.value.name,
                        pricePackage = result.value.pricePackage,
                        packageContents = result.value.packageContents,
                        unitOfMeasurement = result.value.unitOfMeasurement
                    )
                )
            } else {
                ResultStateOperation.Error(Exception("Erro ao ler ingrediente"))
            }
        }
    }

    fun validateIngredient(ingredient: Ingredient): Boolean {
        return ingredient.name.isNotBlank() &&
                ingredient.pricePackage > 0 &&
                ingredient.packageContents > 0
    }

    private fun toIngredient(
        id: Int = 0,
        name: String,
        pricePackage: String,
        packageContents: String,
        unitOfMeasurement: Measurement
    ) = Ingredient(
        id = id,
        name = name,
        pricePackage = pricePackage.replace(",", ".").toDoubleOrNull() ?: 0.0,
        packageContents = packageContents.replace(",", ".").toDoubleOrNull() ?: 0.0,
        unitOfMeasurement = unitOfMeasurement
    )

    fun clearIngredient() {
        _ingredient.value = null
    }
}