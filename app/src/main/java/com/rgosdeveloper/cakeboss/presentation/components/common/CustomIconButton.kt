package com.rgosdeveloper.cakeboss.presentation.components.common

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun <T> CustomIconButton(
    modifier: Modifier = Modifier,
    item: T,
    onClick: (T) -> Unit = {},
    imageVector: ImageVector,
    colorIcon: Color,
    contentDescription: String? = null
) {
    Card(
        modifier = modifier
            .clip(CircleShape),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary.copy(
                0.1f
            ),
            contentColor = MaterialTheme.colorScheme.primary.copy(0.1f),

        ),
    ) {
        IconButton(
            onClick = { onClick(item) },
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = colorIcon,
            )
        }
    }
}