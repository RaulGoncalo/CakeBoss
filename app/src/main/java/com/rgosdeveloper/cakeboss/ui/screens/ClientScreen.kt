package com.rgosdeveloper.cakeboss.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rgosdeveloper.cakeboss.domain.models.Client
import com.rgosdeveloper.cakeboss.ui.components.common.ItemCard

@Composable
fun ClientScreen(
    clients: List<Client>,
    modifier: Modifier = Modifier,
    onDelete: (client: Client) -> Unit,
    onEdit: (client: Client) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(clients.size) { index ->
            val client = clients[index]
            val title = client.name
            val subtitle =
                "Telefone: ${client.phone}"
            val imageVector = Icons.Default.Person

            ItemCard(
                item = client,
                imageVector = imageVector,
                title = title,
                subtitle = subtitle,
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}