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
import androidx.compose.ui.unit.dp
import com.udistrital.gestorcasos.ui.components.DateField

/** Pide la conclusión al cerrar un caso. */
@Composable
fun CloseCaseDialog(onConfirm: (String) -> Unit, onDismiss: () -> Unit) {
    var conclusion by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cerrar caso") },
        text = {
            Column {
                Text("Escribe la conclusión final del caso:")
                OutlinedTextField(
                    value = conclusion,
                    onValueChange = { conclusion = it },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(conclusion) }) { Text("Cerrar caso") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}

/** Registra una nueva entrevista: entrevistado, fecha y hallazgos principales. */
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
        title = { Text("Nueva entrevista") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Entrevistado") },
                    modifier = Modifier.fillMaxWidth()
                )
                DateField(label = "Fecha de la entrevista", dateMillis = date, onDateSelected = { date = it })
                OutlinedTextField(
                    value = findings,
                    onValueChange = { findings = it },
                    label = { Text("Hallazgos principales") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = { TextButton(onClick = { onConfirm(name, date, findings) }) { Text("Guardar") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}
