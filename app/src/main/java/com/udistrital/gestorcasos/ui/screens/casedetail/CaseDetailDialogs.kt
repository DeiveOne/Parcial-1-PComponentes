package com.udistrital.gestorcasos.ui.screens.casedetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.udistrital.gestorcasos.R
import com.udistrital.gestorcasos.ui.components.DateField

/** Asks for final conclusion when closing a case. */
@Composable
fun CloseCaseDialog(onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var conclusion by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.close_case)) },
        text = {
            Column {
                Text(stringResource(R.string.close_case_prompt))
                OutlinedTextField(
                    value = conclusion,
                    onValueChange = { conclusion = it },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(conclusion) }) { Text(stringResource(R.string.close_case)) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) } }
    )
}

/** Registers a new interview: interviewee, date, and main findings. */
@Composable
fun InterviewDialog(
    onConfirm: (name: String, dateMillis: Long, findings: String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(System.currentTimeMillis()) }
    var findings by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.new_interview)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.interviewee)) },
                    modifier = Modifier.fillMaxWidth()
                )
                DateField(label = stringResource(R.string.interview_date), dateMillis = date, onDateSelected = { date = it })
                OutlinedTextField(
                    value = findings,
                    onValueChange = { findings = it },
                    label = { Text(stringResource(R.string.main_findings)) },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(name, date, findings) }) { Text(stringResource(R.string.save)) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) } }
    )
}
