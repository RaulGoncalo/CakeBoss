package com.rgosdeveloper.cakeboss.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rgosdeveloper.cakeboss.domain.models.Ingredient
import com.rgosdeveloper.cakeboss.domain.models.Measurement
import com.rgosdeveloper.cakeboss.domain.models.Recipe
import com.rgosdeveloper.cakeboss.ui.components.recipe.RecipeCard

@Composable
fun ReceiptScreen(
    recipes: List<Recipe>,
    modifier: Modifier = Modifier,
    onDelete: (recipe: Recipe) -> Unit,
    onEdit: (recipe: Recipe) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(recipes.size) { index ->
            RecipeCard(
                recipe = recipes[index], onEdit = onEdit, onDelete = onDelete
            )
        }
    }
}





