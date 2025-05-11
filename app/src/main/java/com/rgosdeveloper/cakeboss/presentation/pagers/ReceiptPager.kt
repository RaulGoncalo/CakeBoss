package com.rgosdeveloper.cakeboss.presentation.pagers

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rgosdeveloper.cakeboss.domain.models.Recipe
import com.rgosdeveloper.cakeboss.presentation.components.recipe.RecipeCard

@Composable
fun ReceiptPager(
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





