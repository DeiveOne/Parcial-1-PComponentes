package com.udistrital.gestorcasos.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.gestorcasos.R
import com.udistrital.gestorcasos.data.model.Case
import com.udistrital.gestorcasos.ui.AppViewModelProvider
import com.udistrital.gestorcasos.ui.components.EmptyState
import com.udistrital.gestorcasos.ui.components.StatusChip
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Home screen: general case summary (totals by status) + recent cases.
 * From here users navigate to "View list" and "Create case".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onVerListado: () -> Unit,
    onCrearCaso: () -> Unit,
    onAbrirCaso: (Long) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.app_name)) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onCrearCaso) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.create_case))
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.general_summary),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                ResumenCasos(
                    total = uiState.totalCases,
                    abiertos = uiState.openCases,
                    enInvestigacion = uiState.investigatingCases,
                    cerrados = uiState.closedCases
                )
            }

            item {
                OutlinedButton(onClick = onVerListado, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.List, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.view_full_case_list))
                }
            }

            item {
                Text(
                    text = stringResource(R.string.recent_cases),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            if (uiState.recentCases.isEmpty()) {
                item {
                    EmptyState(message = stringResource(R.string.no_cases_yet))
                }
            } else {
                items(uiState.recentCases) { caso ->
                    CasoResumenCard(caso = caso, onClick = { onAbrirCaso(caso.id) })
                }
            }
        }
    }
}

@Composable
private fun ResumenCasos(total: Int, abiertos: Int, enInvestigacion: Int, cerrados: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ResumenTarjeta(titulo = stringResource(R.string.total), valor = total, modifier = Modifier.weight(1f))
        ResumenTarjeta(titulo = stringResource(R.string.open), valor = abiertos, modifier = Modifier.weight(1f))
        ResumenTarjeta(titulo = stringResource(R.string.under_investigation), valor = enInvestigacion, modifier = Modifier.weight(1f))
        ResumenTarjeta(titulo = stringResource(R.string.closed), valor = cerrados, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ResumenTarjeta(titulo: String, valor: Int, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = valor.toString(), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(text = titulo, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun CasoResumenCard(caso: Case, onClick: () -> Unit) {
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
