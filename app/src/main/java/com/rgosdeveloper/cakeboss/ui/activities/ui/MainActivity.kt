package com.rgosdeveloper.cakeboss.ui.activities.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.rgosdeveloper.cakeboss.domain.common.ResultStateOperation
import com.rgosdeveloper.cakeboss.domain.models.Client
import com.rgosdeveloper.cakeboss.domain.models.Ingredient
import com.rgosdeveloper.cakeboss.domain.models.Recipe
import com.rgosdeveloper.cakeboss.domain.models.Schedule
import com.rgosdeveloper.cakeboss.ui.activities.ClientActivity
import com.rgosdeveloper.cakeboss.ui.activities.IngredientRegisterActivity
import com.rgosdeveloper.cakeboss.ui.activities.ReceiptActivity
import com.rgosdeveloper.cakeboss.ui.activities.ScheduleActivity
import com.rgosdeveloper.cakeboss.ui.components.common.CustomDialog
import com.rgosdeveloper.cakeboss.ui.components.common.CustomTopBar
import com.rgosdeveloper.cakeboss.ui.components.common.Tabs
import com.rgosdeveloper.cakeboss.ui.models.TabItem
import com.rgosdeveloper.cakeboss.ui.screens.ClientScreen
import com.rgosdeveloper.cakeboss.ui.screens.IngredientScreen
import com.rgosdeveloper.cakeboss.ui.screens.ReceiptScreen
import com.rgosdeveloper.cakeboss.ui.screens.ScheduleScreen
import com.rgosdeveloper.cakeboss.ui.theme.CakeBossTheme
import com.rgosdeveloper.cakeboss.ui.viewmodels.IngredientViewModel
import com.rgosdeveloper.cakeboss.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    var sampleOrders = emptyList<Schedule>()
    val sampleRecipes = emptyList<Recipe>()
    val sampleClients = emptyList<Client>()

    private val ingredientViewModel: IngredientViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tabItems = listOf(
            TabItem(TabItem.TITLE_SCHEDULES, Icons.Outlined.DateRange, Icons.Filled.DateRange),
            TabItem(TabItem.TITLE_CLIENTS, Icons.Outlined.People, Icons.Filled.People),
            TabItem(TabItem.TITLE_RECEIPTS, Icons.Outlined.Receipt, Icons.Filled.Receipt),
            TabItem(
                TabItem.TITLE_INGREDIENTS,
                Icons.AutoMirrored.Outlined.List,
                Icons.AutoMirrored.Filled.List
            )
        )

        setContent {
            CakeBossTheme {
                val pagerState = rememberPagerState { tabItems.size }
                val coroutineScope = rememberCoroutineScope()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CustomTopBar() },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                var index = pagerState.currentPage
                                returnActivityByCurrentPage(index)
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
                        Tabs(tabItems, pagerState, coroutineScope)
                        PagerContent(tabItems, pagerState)
                    }
                }
            }
        }
    }


    private fun returnActivityByCurrentPage(index: Int) {
        when (index) {
            0 -> startActivity(
                Intent(
                    applicationContext,
                    ScheduleActivity::class.java
                )
            )

            1 -> startActivity(
                Intent(
                    applicationContext,
                    ClientActivity::class.java
                )
            )

            2 -> startActivity(
                Intent(
                    applicationContext,
                    ReceiptActivity::class.java
                )
            )

            3 -> startActivity(
                Intent(
                    applicationContext,
                    IngredientRegisterActivity::class.java
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()
        ingredientViewModel.readAllIngredients()
    }




    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun PagerContent(tabItems: List<TabItem>, pagerState: PagerState) {
        val ingredientsState by ingredientViewModel.ingredients.observeAsState()
        var openAlertDialog by remember { mutableStateOf(false) }
        var ingredientToDelete by remember { mutableStateOf<Ingredient?>(null) }

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
                    0 -> ScheduleScreen(
                        sampleOrders,
                        onStatusChange = { schedule, newStatus ->
                            sampleOrders.find { it == schedule }?.status = newStatus

                            Toast.makeText(
                                applicationContext,
                                "Alterando status de ${schedule.client.name} para $newStatus",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onEdit = { schedule ->
                            Toast.makeText(
                                applicationContext,
                                "Editando ${schedule.client.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onDelete = { schedule ->
                            Toast.makeText(
                                applicationContext,
                                "Deletando ${schedule.client.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )

                    1 -> ClientScreen(
                        sampleClients,
                        onEdit = { client ->
                            Toast.makeText(
                                applicationContext,
                                "Editando ${client.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onDelete = { client ->
                            Toast.makeText(
                                applicationContext,
                                "Deletando ${client.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )

                    2 -> ReceiptScreen(
                        sampleRecipes,
                        onEdit = { recipe ->
                            Toast.makeText(
                                applicationContext,
                                "Editando ${recipe.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onDelete = { recipe ->
                            Toast.makeText(
                                applicationContext,
                                "Deletando ${recipe.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )

                    3 -> IngredientScreen(
                        ingredients = when (val result = ingredientsState) {
                            is ResultStateOperation.Success -> result.value
                            else -> emptyList()
                        },
                        onEdit = { ingredient ->
                            val intent = Intent(applicationContext, IngredientRegisterActivity::class.java)
                            intent.putExtra(Constants.PUT_EXTRA_INGREDIENT, ingredient)
                            startActivity(intent)
                        },
                        onDelete = { ingredient ->
                            ingredientToDelete = ingredient
                            openAlertDialog = true
                        }
                    )
                }
            }
        }

        if (openAlertDialog && ingredientToDelete != null) {
            CustomDialog(
                dialogTitle = "Deletar Ingrediente",
                dialogText = "Você tem certeza que deseja deletar o ingrediente ",
                dialogaFancyText = "${ingredientToDelete!!.name}?",
                onConfirmation = {
                    ingredientViewModel.deleteIngredient(ingredientToDelete!!)
                    Toast.makeText(
                        applicationContext,
                        "Deletando ${ingredientToDelete!!.name}",
                        Toast.LENGTH_SHORT
                    ).show()
                    openAlertDialog = false
                },
                onDismissRequest = {
                    openAlertDialog = false
                },
                icon = Icons.Default.Delete
            )
        }
    }

}
