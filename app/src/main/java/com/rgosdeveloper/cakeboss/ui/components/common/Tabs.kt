package com.rgosdeveloper.cakeboss.ui.components.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.rgosdeveloper.cakeboss.ui.models.TabItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(
    tabItems: List<TabItem>,
    pagerState: PagerState,
    coroutineScope: CoroutineScope
) {
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