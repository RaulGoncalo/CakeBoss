package com.rgosdeveloper.cakeboss.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rgosdeveloper.cakeboss.domain.common.ResultStateOperation
import com.rgosdeveloper.cakeboss.domain.models.Ingredient
import com.rgosdeveloper.cakeboss.domain.models.Measurement
import com.rgosdeveloper.cakeboss.presentation.viewmodels.IngredientViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientRegisterScreen(
    ingredientId: Int? = null,
    ingredientViewModel: IngredientViewModel,
    navController: NavController,
) {

    // Trigger a load when the screen opens
    LaunchedEffect(ingredientId) {
        if (ingredientId != null) {
            ingredientViewModel.readIngredientById(ingredientId)
        }
    }

    // Observe the LiveData
    val ingredientState by ingredientViewModel.ingredient.observeAsState()

    val ingredientEdit = when (val result = ingredientState) {
        is ResultStateOperation.Success -> result.value
        else -> null // Loading or Error
    }

    var name by remember { mutableStateOf("") }
    var pricePackage by remember { mutableStateOf("") }
    var packageContents by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf(Measurement.GRAM) }

    LaunchedEffect(ingredientEdit) {
        name = ingredientEdit?.name ?: ""
        pricePackage = ingredientEdit?.pricePackage?.toString() ?: ""
        packageContents = ingredientEdit?.packageContents?.toString() ?: ""
        selectedUnit = ingredientEdit?.unitOfMeasurement ?: Measurement.GRAM
    }


    var isDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Cadastrar Ingrediente") },
                navigationIcon = {
                    IconButton(onClick = {
                        ingredientViewModel.clearIngredient()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do ingrediente") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = pricePackage,
                    onValueChange = { pricePackage = it },
                    label = { Text("Preço da embalagem") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = packageContents,
                    onValueChange = { packageContents = it },
                    label = { Text("Conteúdo da embalagem") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        value = selectedUnit.description,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Unidade de Medida da embalagem") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) }
                    )

                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false }
                    ) {
                        Measurement.values().forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item.description) },
                                onClick = {
                                    selectedUnit = item
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        if (ingredientEdit?.id != null) {
                            ingredientViewModel.updateIngredient(
                                ingredientEdit.id,
                                name,
                                pricePackage,
                                packageContents,
                                selectedUnit
                            )
                        } else {
                            ingredientViewModel.insertIngredient(
                                name,
                                pricePackage,
                                packageContents,
                                selectedUnit
                            )
                        }

                        ingredientViewModel.clearIngredient()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    enabled = name.isNotBlank() && pricePackage.isNotBlank() && packageContents.isNotBlank()
                ) {
                    Text("Salvar Ingrediente")
                }
            }
        }
    }
}
