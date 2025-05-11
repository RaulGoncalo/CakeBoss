package com.rgosdeveloper.cakeboss.presentation.components.recipe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.rgosdeveloper.cakeboss.R
import com.rgosdeveloper.cakeboss.domain.models.Recipe
import com.rgosdeveloper.cakeboss.presentation.components.common.CustomIconButton
import com.rgosdeveloper.cakeboss.presentation.components.ingredient.IngredientList

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeCard(
    recipe: Recipe, onEdit: (Recipe) -> Unit = {}, onDelete: (Recipe) -> Unit = {}
) {
    var showIngredients by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                GlideImage(
                    model = recipe.imageUrl ?: "",
                    contentDescription = recipe.name,
                    modifier = Modifier
                        .width(140.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                    failure = placeholder(R.drawable.default_recipe),
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Informações sobre a receita

                    Row(
                        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Column {
                                ItemInfoRecipe(Icons.Default.Timer, "${recipe.duration} min")
                                Spacer(modifier = Modifier.width(16.dp))
                                ItemInfoRecipe(Icons.Default.AttachMoney, "R$ ${recipe.totalCost}")
                            }
                        }
                        VerticalDivider(modifier = Modifier.height(80.dp))
                        Column(
                            modifier = Modifier
                                .weight(1.2f)
                                .padding(start = 8.dp)
                        ) {
                            Column {
                                ItemInfoRecipe(Icons.Default.List, "${recipe.portions} porções")
                                Spacer(modifier = Modifier.width(16.dp))
                                ItemInfoRecipe(
                                    Icons.Default.MonetizationOn, "R$ ${recipe.costPerPortion}"
                                )
                            }
                        }
                    }
                }
            }

            //Listagem de ingredientes
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            ) {
                Card(
                    modifier = Modifier.padding(horizontal = 8.dp).weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(
                            0.1f
                        )
                    ),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Ingredientes",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        IconButton(onClick = { showIngredients = !showIngredients }) {
                            Icon(
                                imageVector = if (showIngredients) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    AnimatedVisibility(visible = showIngredients) {
                        Column {
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                            IngredientList(ingredients = recipe.ingredients)
                        }
                    }
                }

                Row{

                    CustomIconButton(
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                        item = recipe,
                        onClick = onEdit,
                        imageVector = Icons.Default.Edit,
                        colorIcon = MaterialTheme.colorScheme.primary
                    )

                    CustomIconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        item = recipe,
                        onClick = onDelete,
                        imageVector = Icons.Default.Delete,
                        colorIcon = Color.Red.copy(alpha = 0.5f)
                    )

                }
            }
        }
    }
}