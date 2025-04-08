package com.rgosdeveloper.cakeboss.ui.activities

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.rgosdeveloper.cakeboss.domain.common.ResultStateOperation
import com.rgosdeveloper.cakeboss.domain.models.Client
import com.rgosdeveloper.cakeboss.domain.models.Recipe
import com.rgosdeveloper.cakeboss.domain.models.Schedule
import com.rgosdeveloper.cakeboss.ui.components.common.CustomTopBar
import com.rgosdeveloper.cakeboss.ui.models.TabItem
import com.rgosdeveloper.cakeboss.ui.screens.ClientScreen
import com.rgosdeveloper.cakeboss.ui.screens.IngredientScreen
import com.rgosdeveloper.cakeboss.ui.screens.ReceiptScreen
import com.rgosdeveloper.cakeboss.ui.screens.ScheduleScreen
import com.rgosdeveloper.cakeboss.ui.theme.CakeBossTheme
import com.rgosdeveloper.cakeboss.ui.viewmodels.IngredientViewModel
import com.rgosdeveloper.cakeboss.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
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

    override fun onStart() {
        super.onStart()
        ingredientViewModel.readAllIngredients()
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Tabs(tabItems: List<TabItem>, pagerState: PagerState, coroutineScope: CoroutineScope) {
        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = tabItem.title, fontSize = 10.sp) },
                    icon = {
                        Icon(
                            imageVector = if (pagerState.currentPage == index) tabItem.selectedIcon else tabItem.unselectedIcon,
                            contentDescription = tabItem.title
                        )
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun PagerContent(tabItems: List<TabItem>, pagerState: PagerState) {
        val ingredientsState by ingredientViewModel.ingredients.observeAsState()

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
                            else -> emptyList()  // Caso esteja carregando ou tenha erro
                        },
                        onEdit = { ingredient ->
                            val intent =
                                Intent(applicationContext, IngredientRegisterActivity::class.java)
                            intent.putExtra(Constants.PUT_EXTRA_INGREDIENT, ingredient)
                            startActivity(intent)
                        },
                        onDelete = { ingredient ->
                            ingredientViewModel.deleteIngredient(ingredient)
                            ingredientViewModel.readAllIngredients()
                        }
                    )
                }
            }
        }
    }
}
