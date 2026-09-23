package com.udistrital.gestorcasos.ui.components

import androidx.compose.material3.AssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.udistrital.gestorcasos.data.model.CaseStatus

/**
 * Chip de color que representa el estado de un caso. Se reutiliza en Home,
 * en el listado y en el detalle para que el estado sea visible de un vistazo.
 */
@Composable
fun StatusChip(status: CaseStatus, modifier: Modifier = Modifier) {
    val color = when (status) {
        CaseStatus.ABIERTO -> Color(0xFF2E7D32)
        CaseStatus.EN_INVESTIGACION -> Color(0xFFB26A00)
        CaseStatus.CERRADO -> Color(0xFF757575)
    }
    AssistChip(
        onClick = {},
        label = { Text(text = status.label, color = color) },
        modifier = modifier
    )
}
