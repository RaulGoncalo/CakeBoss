package com.rgosdeveloper.cakeboss.presentation.components.schedule

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rgosdeveloper.cakeboss.domain.models.Schedule
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@SuppressLint("NewApi")
@Composable
fun DaysList(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    orders: List<Schedule>,
    onDateSelected: (LocalDate) -> Unit
) {
    val daysInMonth = currentMonth.lengthOfMonth()
    val daysList = (1..daysInMonth).map { day -> currentMonth.atDay(day) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedDate) {
        val selectedIndex = daysList.indexOf(selectedDate)
        if (selectedIndex != -1) {
            coroutineScope.launch {
                listState.animateScrollToItem(
                    index = selectedIndex // Move o dia selecionado para a esquerda
                )
            }
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        items(daysList) { date ->
            DayItem(
                date = date,
                isSelected = date == selectedDate,
                hasOrders = orders.any { it.date == date },
                onClick = { onDateSelected(date) }
            )
        }
    }
}