package com.udistrital.gestorcasos.ui.screens.casedetail

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.udistrital.gestorcasos.R
import com.udistrital.gestorcasos.data.model.CaseStatus
import com.udistrital.gestorcasos.data.model.Interview
import com.udistrital.gestorcasos.ui.AppViewModelProvider
import com.udistrital.gestorcasos.ui.components.ConfirmDialog
import com.udistrital.gestorcasos.ui.components.EmptyState
import com.udistrital.gestorcasos.ui.components.StatusChip
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Case details: general data, conclusion (if closed), list of
 * interviews with main findings, and actions to edit, close, and delete.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaseDetailScreen(
    caseId: Long,
    onBack: () -> Unit,
    onEditar: (Long) -> Unit,
    viewModel: CaseDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val deleted by viewModel.deleted.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showAddInterview by remember { mutableStateOf(false) }
    var showCloseDialog by remember { mutableStateOf(false) }

    LaunchedEffect(caseId) { viewModel.load(caseId) }
    LaunchedEffect(deleted) { if (deleted) onBack() }

    val caso = uiState.case

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(caso?.title ?: stringResource(R.string.case_detail)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    IconButton(onClick = { onEditar(caseId) }) {
                        Icon(Icons.Default.Edit, contentDescription = stringResource(R.string.edit))
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddInterview = true }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_interview))
            }
        }
    ) { paddingValues ->
        if (caso == null) {
            EmptyState(message = stringResource(R.string.loading_case), modifier = Modifier.fillMaxSize().padding(paddingValues))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatusChip(status = caso.status)

                Text(text = caso.description, style = MaterialTheme.typography.bodyMedium)

                Text(
                    text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(caso.date),
                    style = MaterialTheme.typography.bodySmall
                )

                if (caso.status != CaseStatus.CERRADO) {
                    OutlinedButton(onClick = { showCloseDialog = true }, modifier = Modifier.fillMaxWidth()) {
                        Text(stringResource(R.string.close_case))
                    }
                } else if (!caso.conclusion.isNullOrBlank()) {
                    Text(stringResource(R.string.conclusion), style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text(caso.conclusion)
                }

                Text(stringResource(R.string.interviews), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                if (uiState.interviews.isEmpty()) {
                    EmptyState(message = stringResource(R.string.no_interviews_yet))
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(uiState.interviews) { entrevista ->
                            InterviewCard(entrevista)
                        }
                    }
                }
            }
        }
    }

    if (showDeleteDialog) {
        ConfirmDialog(
            title = stringResource(R.string.delete_case_title),
            message = stringResource(R.string.delete_case_message),
            onConfirm = {
                showDeleteDialog = false
                viewModel.deleteCase()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    if (showCloseDialog) {
        CloseCaseDialog(
            onConfirm = { conclusion ->
                showCloseDialog = false
                viewModel.updateStatus(CaseStatus.CERRADO, conclusion)
            },
            onDismiss = { showCloseDialog = false }
        )
    }

    if (showAddInterview) {
        InterviewDialog(
            onConfirm = { name, date, findings ->
                showAddInterview = false
                viewModel.addInterview(name, date, findings)
            },
            onDismiss = { showAddInterview = false }
        )
    }
}

@Composable
private fun InterviewCard(interview: Interview) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = interview.intervieweeName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            Text(
                text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(interview.date),
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = interview.findings, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
