package com.rgosdeveloper.cakeboss.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.rgosdeveloper.cakeboss.domain.models.Client
import com.rgosdeveloper.cakeboss.domain.models.Ingredient
import com.rgosdeveloper.cakeboss.domain.models.Measurement
import com.rgosdeveloper.cakeboss.domain.models.Recipe
import com.rgosdeveloper.cakeboss.domain.models.Schedule
import com.rgosdeveloper.cakeboss.ui.components.common.CustomTopBar
import com.rgosdeveloper.cakeboss.ui.models.TabItem
import com.rgosdeveloper.cakeboss.ui.screens.ClientScreen
import com.rgosdeveloper.cakeboss.ui.screens.IngredientScreen
import com.rgosdeveloper.cakeboss.ui.screens.ReceiptScreen
import com.rgosdeveloper.cakeboss.ui.screens.ScheduleScreen
import com.rgosdeveloper.cakeboss.ui.theme.CakeBossTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    var sampleOrders = listOf(
        Schedule(
            client = Client("Ana Souza", "11987654321"),
            recipe = Recipe(
                name = "Bolo de Chocolate",
                imageUrl = null,
                duration = 60,
                totalCost = 50.0,
                portions = 10,
                costPerPortion = 5.0,
                ingredients = emptyList()
            ),
            date = LocalDate.now(), // Pedido para daqui a 2 dias
            quantity = 2,
            status = com.rgosdeveloper.cakeboss.domain.models.Status.PENDING
        ),
        Schedule(
            client = Client("Ana Souza", "11987654321"),
            recipe = Recipe(
                name = "Bolo de Chocolate",
                imageUrl = null,
                duration = 60,
                totalCost = 50.0,
                portions = 10,
                costPerPortion = 5.0,
                ingredients = emptyList()
            ),
            date = LocalDate.now().plusDays(2), // Pedido para daqui a 2 dias
            quantity = 2,
            status = com.rgosdeveloper.cakeboss.domain.models.Status.PENDING
        ),
        Schedule(
            client = Client("Carlos Oliveira", "11912345678"),
            recipe = Recipe(
                name = "Cheesecake de Morango",
                imageUrl = null,
                duration = 90,
                totalCost = 80.0,
                portions = 8,
                costPerPortion = 10.0,
                ingredients = emptyList()
            ),
            date = LocalDate.now().plusDays(5), // Pedido para daqui a 5 dias
            quantity = 1,
            status = com.rgosdeveloper.cakeboss.domain.models.Status.PENDING
        ),
        Schedule(
            client = Client("Mariana Silva", "11999887766"),
            recipe = Recipe(
                name = "Torta de Limão",
                imageUrl = null,
                duration = 75,
                totalCost = 60.0,
                portions = 6,
                costPerPortion = 10.0,
                ingredients = emptyList()
            ),
            date = LocalDate.now().plusDays(5), // Mesmo dia do pedido anterior
            quantity = 3,
            status = com.rgosdeveloper.cakeboss.domain.models.Status.PENDING
        ),
        Schedule(
            client = Client("João Pedro", "11966554433"),
            recipe = Recipe(
                name = "Brownie",
                imageUrl = null,
                duration = 40,
                totalCost = 30.0,
                portions = 5,
                costPerPortion = 6.0,
                ingredients = emptyList()
            ),
            date = LocalDate.now().plusDays(8), // Pedido para daqui a 8 dias
            quantity = 4,
            status = com.rgosdeveloper.cakeboss.domain.models.Status.PENDING
        )
    )
    val sampleRecipes = listOf(
        Recipe(
            name = "Lasanha de Frango", duration = 45, imageUrl = null, ingredients = listOf(
                Ingredient("Massa", 10.0, 500.0, Measurement.GRAM),
                Ingredient("Frango", 25.0, 300.0, Measurement.GRAM),
                Ingredient("Molho Branco Diretamenta da Sucecia Europa", 8.0, 200.0, Measurement.MILLILITER),
                Ingredient("Queijo", 20.0, 200.0, Measurement.GRAM)
            ), totalCost = 63.0, portions = 4, costPerPortion = 15.75
        ), Recipe(
            name = "Lasanha de Frango",
            duration = 45,
            imageUrl = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTGEuEBSvWesRMtc9TkRQDLkQreUclqWyaSQUEHIpzsdbmHn3qepCkNFO9hwLQ3qZSyx1QNAL8xabnnn6yv1r4cqzkyxMXXKhVv4aDECuE",
            ingredients = listOf(
                Ingredient("Massa", 10.0, 500.0, Measurement.GRAM),
                Ingredient("Frango", 25.0, 300.0, Measurement.GRAM),
                Ingredient("Molho Branco", 8.0, 200.0, Measurement.MILLILITER),
                Ingredient("Queijo", 20.0, 200.0, Measurement.GRAM)
            ),
            totalCost = 63.0,
            portions = 4,
            costPerPortion = 15.75
        ), Recipe(
            name = "Bolo de Chocolate",
            duration = 60,
            imageUrl = "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcQHofJH5w7_aFX0EoU1Zmdj4EoCQx-c09LRj3YNv9t45AVPREj7QPQXLRJ1fLn8z8pjgAn9hd-6R4oLCUHhr6IWzpRF8K_GiJno17US",
            ingredients = listOf(
                Ingredient("Farinha", 5.0, 250.0, Measurement.GRAM),
                Ingredient("Ovos", 10.0, 4.0, Measurement.UNIT),
                Ingredient("Chocolate", 15.0, 200.0, Measurement.GRAM),
                Ingredient("Açúcar", 3.0, 150.0, Measurement.GRAM)
            ),
            totalCost = 33.0,
            portions = 6,
            costPerPortion = 5.50
        )
    )
    val sampleClients = listOf(
        Client("João", "123456789"),
        Client("Maria", "987654321"),
        Client("Pedro", "111222333"),
        Client("Ana", "444555666"),
    )
    val sampleIngredients = listOf(
        Ingredient("Ovo", 15.0, 12.0, Measurement.UNIT),
        Ingredient("Leite", 16.0, 1.0, Measurement.LITER),
        Ingredient("Açucar", 12.5, 5.0, Measurement.KILOGRAM),
    )

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
                    topBar = { CustomTopBar() }
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
                        sampleIngredients,
                        onEdit = { ingredient ->
                            Toast.makeText(
                                applicationContext,
                                "Editando ${ingredient.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onDelete = { ingredient ->
                            Toast.makeText(
                                applicationContext,
                                "Deletando ${ingredient.name}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}
