package com.rgosdeveloper.cakeboss.presentation.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rgosdeveloper.cakeboss.presentation.pagers.MainPager
import com.rgosdeveloper.cakeboss.presentation.screens.IngredientRegisterScreen
import com.rgosdeveloper.cakeboss.presentation.theme.CakeBossTheme
import com.rgosdeveloper.cakeboss.presentation.viewmodels.IngredientViewModel
import com.rgosdeveloper.cakeboss.navigation.Routes
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val ingredientViewModel: IngredientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CakeBossTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.MAIN_PAGER
                ) {
                        composable(Routes.MAIN_PAGER) {
                        MainPager(
                            ingredientViewModel = ingredientViewModel,
                            navController = navController
                        )
                    }
                    composable(
                        route = Routes.INGREDIENT_REGISTER,
                        arguments = listOf(
                            navArgument(Routes.Arguments.INGREDIENT_ID) {
                                type = NavType.StringType
                                nullable = true
                            }
                        )
                    ) { backStackEntry ->
                        val ingredientIdStr = backStackEntry.arguments?.getString(Routes.Arguments.INGREDIENT_ID)
                        val ingredientId = ingredientIdStr?.toIntOrNull()

                        IngredientRegisterScreen(
                            ingredientId = ingredientId,
                            ingredientViewModel = ingredientViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
