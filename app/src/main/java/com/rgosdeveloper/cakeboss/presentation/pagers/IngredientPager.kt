package com.rgosdeveloper.cakeboss.presentation.pagers

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rgosdeveloper.cakeboss.domain.common.ResultStateOperation
import com.rgosdeveloper.cakeboss.domain.models.Ingredient
import com.rgosdeveloper.cakeboss.navigation.Routes
import com.rgosdeveloper.cakeboss.presentation.components.common.CustomDialog
import com.rgosdeveloper.cakeboss.presentation.components.common.ItemCard
import com.rgosdeveloper.cakeboss.presentation.viewmodels.IngredientViewModel
import kotlinx.coroutines.launch

@Composable
fun IngredientPager(
    ingredientViewModel: IngredientViewModel,
    context: Context = LocalContext.current,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val ingredientsState by ingredientViewModel.ingredients.observeAsState()
    val ingredients = when (val result = ingredientsState) {
        is ResultStateOperation.Success -> result.value
        is ResultStateOperation.Error -> {
            Toast.makeText(context, "Erro ao carregar ingredientes", Toast.LENGTH_SHORT)
                .show()
            emptyList()
        }

        else -> emptyList()
    }

    var ingredientToDelete by remember { mutableStateOf<Ingredient?>(null) }
    var openAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
                modifier = Modifier.padding(bottom = 64.dp)
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {
            items(ingredients.size) { index ->
                val ingredient = ingredients[index]
                val title = ingredient.name
                val subtitle =
                    "R$ ${ingredient.pricePackage} - ${ingredient.packageContents} ${ingredient.unitOfMeasurement.unit}"

                ItemCard(
                    item = ingredient,
                    imageVector = Icons.Default.FoodBank,
                    title = title,
                    subtitle = subtitle,
                    onEdit = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            Routes.Arguments.INGREDIENT_ID,
                            ingredient
                        )

                        navController.navigate(ingredient.id?.let { it1 ->
                            Routes.ingredientRegisterRoute(
                                it1
                            )
                        } ?: Routes.MAIN_PAGER)
                    },
                    onDelete = {
                        ingredientToDelete = ingredient
                        openAlertDialog = true
                    }
                )
            }
        }
    }

    if (openAlertDialog && ingredientToDelete != null) {
        CustomDialog(
            dialogTitle = "Deletar Ingrediente",
            dialogText = "Você tem certeza que deseja deletar o ingrediente ",
            dialogaFancyText = "${ingredientToDelete!!.name}?",
            onConfirmation = {
                if (ingredientToDelete != null) {
                    ingredientViewModel.deleteIngredient(ingredientToDelete!!)
                }
                openAlertDialog = false

                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Ingrediente deletado com sucesso",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onDismissRequest = {
                openAlertDialog = false
            },
            icon = Icons.Default.Delete
        )
    }
}
