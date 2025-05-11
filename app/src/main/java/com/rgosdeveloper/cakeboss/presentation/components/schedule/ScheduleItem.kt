package com.rgosdeveloper.cakeboss.presentation.components.schedule

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.rgosdeveloper.cakeboss.R
import com.rgosdeveloper.cakeboss.domain.models.Schedule
import com.rgosdeveloper.cakeboss.domain.models.Status
import com.rgosdeveloper.cakeboss.presentation.components.common.CustomIconButton
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ScheduleItem(
    schedule: Schedule,
    onStatusChange: (Schedule, Status) -> Unit,
    onEdit: (Schedule) -> Unit,
    onDelete: (Schedule) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Imagem da Receita
                GlideImage(
                    model = schedule.recipe.imageUrl ?: "",
                    contentDescription = schedule.recipe.name,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    failure = placeholder(R.drawable.default_recipe),
                )
                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    // Nome do Cliente
                    Text(
                        text = schedule.client.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    // Data do Agendamento
                    Text(
                        text = schedule.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Receita e Quantidade
                    Text(
                        text = "${schedule.recipe.name} - ${schedule.quantity}x",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // Preço Total
                    Text(
                        text = "R$ ${"%.2f".format(schedule.totalPrice)}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )
                }

                // Ícones de Editar e Deletar
                Row {
                    CustomIconButton(
                        modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                        item = schedule,
                        onClick = onEdit,
                        imageVector = Icons.Default.Edit,
                        colorIcon = MaterialTheme.colorScheme.primary
                    )

                    CustomIconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        item = schedule,
                        onClick = onDelete,
                        imageVector = Icons.Default.Delete,
                        colorIcon = Color.Red.copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown para mudar o status
            StatusDropdown(
                currentStatus = schedule.status,
                onStatusSelected = { newStatus -> onStatusChange(schedule, newStatus) }
            )
        }
    }
}

@Composable
fun StatusDropdown(
    currentStatus: Status,
    onStatusSelected: (Status) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf(currentStatus) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                MaterialTheme.colorScheme.primary.copy(
                    0.1f
                )
            )
            .clickable { expanded = true }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = selectedStatus.name.replace("_", " ").capitalize(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        Status.values().forEach { status ->
            DropdownMenuItem(
                text = { Text(status.name.replace("_", " ").capitalize()) },
                onClick = {
                    selectedStatus = status
                    onStatusSelected(status)
                    expanded = false
                }
            )
        }
    }
}
