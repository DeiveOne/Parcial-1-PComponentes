package com.udistrital.gestorcasos.ui.screens.caseform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.gestorcasos.R
import com.udistrital.gestorcasos.data.model.CaseStatus
import com.udistrital.gestorcasos.ui.AppViewModelProvider
import com.udistrital.gestorcasos.ui.components.DateField

/**
 * Case creation / editing form. The case status is selected using
 * FilterChip, and if "Closed" is selected, the conclusion field is shown.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseFormScreen(
    caseId: Long?,
    onBack: () -> Unit,
    onSaved: () -> Unit,
    viewModel: CaseFormViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(caseId) { viewModel.load(caseId) }
    LaunchedEffect(uiState.saved) { if (uiState.saved) onSaved() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (uiState.isEditing) stringResource(R.string.edit_case) else stringResource(R.string.new_case)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.title,
                onValueChange = viewModel::onTitleChange,
                label = { Text(stringResource(R.string.title)) },
                isError = uiState.titleError,
                modifier = Modifier.fillMaxWidth()
            )
            if (uiState.titleError) {
                Text(
                    text = stringResource(R.string.title_required),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::onDescriptionChange,
                label = { Text(stringResource(R.string.description)) },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            DateField(
                label = stringResource(R.string.case_date),
                dateMillis = uiState.dateMillis,
                onDateSelected = viewModel::onDateChange
            )

            Text(stringResource(R.string.status), style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CaseStatus.entries.forEach { status ->
                    FilterChip(
                        selected = uiState.status == status,
                        onClick = { viewModel.onStatusChange(status) },
                        label = { Text(status.label) }
                    )
                }
            }

            if (uiState.status == CaseStatus.CERRADO) {
                OutlinedTextField(
                    value = uiState.conclusion,
                    onValueChange = viewModel::onConclusionChange,
                    label = { Text(stringResource(R.string.conclusion)) },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = viewModel::save, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.save))
            }
        }
    }
}
