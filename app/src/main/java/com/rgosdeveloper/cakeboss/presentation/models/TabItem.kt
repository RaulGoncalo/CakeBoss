package com.rgosdeveloper.cakeboss.presentation.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
) {
    companion object {
        const val TITLE_SCHEDULES = "Agendamentos"
        const val TITLE_CLIENTS = "Clientes"
        const val TITLE_RECEIPTS = "Receitas"
        const val TITLE_INGREDIENTS = "Ingredientes"

        val tabItems = listOf(
            TabItem(
                TITLE_SCHEDULES,
                Icons.Outlined.DateRange,
                Icons.Filled.DateRange
            ),
            TabItem(
                TITLE_CLIENTS,
                Icons.Outlined.People,
                Icons.Filled.People
            ),
            TabItem(
                TITLE_RECEIPTS,
                Icons.Outlined.Receipt,
                Icons.Filled.Receipt
            ),
            TabItem(
                TITLE_INGREDIENTS,
                Icons.AutoMirrored.Outlined.List,
                Icons.AutoMirrored.Filled.List
            )
        )
    }
}



