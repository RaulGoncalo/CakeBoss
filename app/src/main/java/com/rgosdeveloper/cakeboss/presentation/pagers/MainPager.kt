package com.rgosdeveloper.cakeboss.presentation.pagers

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.rgosdeveloper.cakeboss.navigation.Routes
import com.rgosdeveloper.cakeboss.presentation.components.common.CustomTopBar
import com.rgosdeveloper.cakeboss.presentation.components.common.PagerContent
import com.rgosdeveloper.cakeboss.presentation.components.common.Tabs
import com.rgosdeveloper.cakeboss.presentation.models.TabItem
import com.rgosdeveloper.cakeboss.presentation.viewmodels.IngredientViewModel

@Composable
fun MainPager(
    modifier: Modifier = Modifier,
    ingredientViewModel: IngredientViewModel,
    navController: NavHostController,
) {
    val pagerState = rememberPagerState { TabItem.tabItems.size }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { CustomTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    var index = pagerState.currentPage
                    openScreenByPage(index = index, navController)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Tabs(pagerState, coroutineScope)
            PagerContent(
                pagerState,
                ingredientViewModel,
                navController
            )
        }
    }
}

private fun openScreenByPage(index: Int, navController: NavController) {
    when (index) {
        0 -> navController.navigate("ingredient_register")
        1 -> navController.navigate("ingredient_register")
        2 -> navController.navigate("ingredient_register")
        3 -> navController.navigate(Routes.INGREDIENT_REGISTER)
    }
}