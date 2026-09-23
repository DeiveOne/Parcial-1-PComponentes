package co.edu.udistrital.parcial01_pc.ui.theme.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.edu.udistrital.parcial01_pc.Domain.Model.Case
import co.edu.udistrital.parcial01_pc.Domain.Model.CaseStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CaseItem(
    case: Case,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }, // Cuando tocan la tarjeta, ejecutamos la acción
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = case.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Indicador visual de Estado (OPEN = Verde, CLOSED = Rojo)
                val statusColor = if (case.status == CaseStatus.OPEN) Color(0xFF4CAF50) else Color(0xFFF44336)
                val statusText = if (case.status == CaseStatus.OPEN) "ABIERTO" else "CERRADO"

                Surface(
                    color = statusColor.copy(alpha = 0.2f), // Fondo semitransparente
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = statusText,
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = case.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))


            val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
            val dateString = sdf.format(Date(case.dateMillis))

            Text(
                text = "Registrado el: $dateString",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}