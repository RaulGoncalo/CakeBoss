package com.rgosdeveloper.cakeboss.presentation.components.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.rgosdeveloper.cakeboss.presentation.pagers.ClientPager
import com.rgosdeveloper.cakeboss.presentation.pagers.IngredientPager
import com.rgosdeveloper.cakeboss.presentation.pagers.ReceiptPager
import com.rgosdeveloper.cakeboss.presentation.pagers.SchedulePager
import com.rgosdeveloper.cakeboss.presentation.viewmodels.IngredientViewModel

@Composable
fun PagerContent(
    pagerState: PagerState,
    ingredientViewModel: IngredientViewModel,
    navController: NavController
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
    ) { index ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (index) {
                0 -> SchedulePager(
                    orders = emptyList(),
                    onStatusChange = { schedule, status ->
                        // Handle status change
                    },
                    onEdit = { schedule ->
                        // Handle edit action
                    },
                    onDelete = { schedule ->
                        // Handle delete action
                    }
                )

                1 -> ClientPager(
                    clients = emptyList(),
                    onEdit = { client ->
                        // Handle edit action
                    },
                    onDelete = { client ->
                        // Handle delete action
                    }
                )

                2 -> ReceiptPager(
                    recipes = emptyList(),
                    onEdit = { recipe ->
                        // Handle edit action
                    },
                    onDelete = { recipe ->
                        // Handle delete action
                    }
                )

                3 -> IngredientPager(
                    ingredientViewModel = ingredientViewModel,
                    context = LocalContext.current,
                    navController
                )
            }
        }
    }
}