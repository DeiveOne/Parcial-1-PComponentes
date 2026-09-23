package com.udistrital.gestorcasos.ui.screens.caselist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.gestorcasos.data.model.Case
import com.udistrital.gestorcasos.ui.AppViewModelProvider
import com.udistrital.gestorcasos.ui.components.EmptyState
import com.udistrital.gestorcasos.ui.components.SearchField
import com.udistrital.gestorcasos.ui.components.StatusChip
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Listado de casos: buscador por título + estado visible en cada tarjeta.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseListScreen(
    onBack: () -> Unit,
    onAbrirCaso: (Long) -> Unit,
    viewModel: CaseListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val query by viewModel.query.collectAsState()
    val cases by viewModel.cases.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listado de casos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            SearchField(query = query, onQueryChange = viewModel::onQueryChange)
            Spacer(modifier = Modifier.height(12.dp))

            if (cases.isEmpty()) {
                EmptyState(
                    message = if (query.isBlank()) {
                        "Todavía no hay casos registrados."
                    } else {
                        "No se encontraron casos para \"$query\"."
                    }
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(cases) { caso ->
                        CaseListItem(caso = caso, onClick = { onAbrirCaso(caso.id) })
                    }
                }
            }
        }
    }
}

@Composable
private fun CaseListItem(caso: Case, onClick: () -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = caso.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(
                text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(caso.date),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            StatusChip(status = caso.status)
        }
    }
}
