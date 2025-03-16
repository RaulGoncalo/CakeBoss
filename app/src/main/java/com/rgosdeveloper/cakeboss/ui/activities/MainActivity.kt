package com.rgosdeveloper.cakeboss.ui.activities

import android.os.Bundle
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
import com.rgosdeveloper.cakeboss.ui.components.CustomTopBar
import com.rgosdeveloper.cakeboss.ui.models.TabItem
import com.rgosdeveloper.cakeboss.ui.theme.CakeBossTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

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
            Text(text = "Página ${tabItems[index].title}")
        }
    }
}