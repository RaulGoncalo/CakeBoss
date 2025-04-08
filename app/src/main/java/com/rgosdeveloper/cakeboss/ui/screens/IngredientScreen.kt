package com.rgosdeveloper.cakeboss.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FoodBank
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rgosdeveloper.cakeboss.domain.models.Ingredient
import com.rgosdeveloper.cakeboss.domain.models.Measurement
import com.rgosdeveloper.cakeboss.ui.components.common.ItemCard

@Composable
fun IngredientScreen(
    ingredients: List<Ingredient>,
    modifier: Modifier = Modifier,
    onDelete: (ingredient: Ingredient) -> Unit,
    onEdit: (ingredient: Ingredient) -> Unit
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(ingredients.size) { index ->
            val ingredient = ingredients[index]
            val title = ingredient.name
            val subtitle =
                "R$ ${ingredient.pricePackage} - ${ingredient.packageContents} ${ingredient.unitOfMeasurement.unit}"
            val imageVector = Icons.Default.FoodBank

            ItemCard(
                item = ingredient,
                imageVector = imageVector,
                title = title,
                subtitle = subtitle,
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}
