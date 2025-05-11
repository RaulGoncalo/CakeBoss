package com.rgosdeveloper.cakeboss.presentation.pagers

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rgosdeveloper.cakeboss.domain.models.Schedule
import com.rgosdeveloper.cakeboss.domain.models.Status
import com.rgosdeveloper.cakeboss.presentation.components.schedule.DaysList
import com.rgosdeveloper.cakeboss.presentation.components.schedule.ScheduleList
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("NewApi")
@Composable
fun SchedulePager(
    orders: List<Schedule>,
    onStatusChange: (Schedule, Status) -> Unit,
    onEdit: (Schedule) -> Unit,
    onDelete: (Schedule) -> Unit
) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val currentMonth = remember { YearMonth.now() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        Column {
            Text(
                text = "${
                    currentMonth.month.getDisplayName(
                        TextStyle.FULL,
                        Locale("pt", "BR")
                    )
                } ${currentMonth.year}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                DaysList(
                    currentMonth = currentMonth,
                    selectedDate = selectedDate,
                    orders = orders,
                    onDateSelected = { selectedDate = it }
                )
            }
        }
        ScheduleList(orders, selectedDate, onStatusChange, onEdit, onDelete)
    }
}






