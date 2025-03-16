package com.rgosdeveloper.cakeboss.ui.models

import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
){
    companion object {
        const val TITLE_SCHEDULES = "Agendamentos"
        const val TITLE_CLIENTS = "Clientes"
        const val TITLE_RECEIPTS = "Receitas"
        const val TITLE_INGREDIENTS = "Ingredientes"
    }
}

