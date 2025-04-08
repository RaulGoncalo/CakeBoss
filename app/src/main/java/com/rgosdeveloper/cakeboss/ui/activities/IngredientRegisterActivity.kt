package com.rgosdeveloper.cakeboss.ui.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rgosdeveloper.cakeboss.domain.models.Ingredient
import com.rgosdeveloper.cakeboss.domain.models.Measurement
import com.rgosdeveloper.cakeboss.ui.activities.ui.theme.CakeBossTheme
import com.rgosdeveloper.cakeboss.ui.viewmodels.IngredientViewModel
import com.rgosdeveloper.cakeboss.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class IngredientRegisterActivity : ComponentActivity() {

    private val ingredientViewModel: IngredientViewModel by viewModels()


    private var ingredientEdit: Ingredient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.extras
        if (bundle != null) {
            ingredientEdit = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(Constants.PUT_EXTRA_INGREDIENT, Ingredient::class.java)
            } else {
                bundle.getParcelable(Constants.PUT_EXTRA_INGREDIENT)
            }
        }

        enableEdgeToEdge()
        setContent {
            CakeBossTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Cadastrar Ingrediente") },
                            navigationIcon = {
                                IconButton(
                                    onClick = { finish() }
                                ) {
                                    Icon(Icons.Default.ArrowBack, "Voltar")
                                }

                            }
                        )
                    }
                ) { innerPadding ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        IngredientScreen(ingredientEdit) { ingredient ->
                            if (ingredientEdit != null) {
                                ingredientViewModel.updateIngredient(ingredient)
                            }
                            ingredientViewModel.insertIngredient(ingredient)
                            finish()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientScreen(
    ingredientEdit: Ingredient? = null,
    onIngredientSaved: (Ingredient) -> Unit
) {
    var name by remember { mutableStateOf(ingredientEdit?.name ?: "") }
    var pricePackage by remember { mutableStateOf(ingredientEdit?.pricePackage?.toString() ?: "") }
    var packageContents by remember {
        mutableStateOf(
            ingredientEdit?.packageContents?.toString() ?: ""
        )
    }

    //Dropdown State
    var selectedUnit by remember {
        mutableStateOf(
            ingredientEdit?.unitOfMeasurement ?: Measurement.GRAM
        )
    }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Campo de Nome
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome do ingrediente") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de Preço
        OutlinedTextField(
            value = pricePackage,
            onValueChange = { pricePackage = it },
            label = { Text("Preço da embalagem") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Campo de Conteúdo da Embalagem
        OutlinedTextField(
            value = packageContents,
            onValueChange = { packageContents = it },
            label = { Text("Conteúdo da embalagem") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown de Unidade de Medida
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

        // Botão de Salvar
        Button(
            onClick = {
                val ingredient = Ingredient(
                    id = ingredientEdit?.id ?: null,
                    name = name,
                    pricePackage = pricePackage.toDoubleOrNull() ?: 0.0,
                    packageContents = packageContents.toDoubleOrNull() ?: 0.0,
                    unitOfMeasurement = selectedUnit
                )
                onIngredientSaved(ingredient)
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