package com.rgosdeveloper.cakeboss.ui.components.schedule

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rgosdeveloper.cakeboss.domain.models.Schedule
import com.rgosdeveloper.cakeboss.domain.models.Status
import java.time.LocalDate

@Composable
fun ScheduleList(
    orders: List<Schedule>,
    selectedDate: LocalDate,
    onStatusChange: (Schedule, Status) -> Unit,
    onEdit: (Schedule) -> Unit,
    onDelete: (Schedule) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 8.dp)) {
        items(orders) { schedule ->
            if(schedule.date == selectedDate){
                ScheduleItem(
                    schedule = schedule,
                    onStatusChange = onStatusChange,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        }
    }
}