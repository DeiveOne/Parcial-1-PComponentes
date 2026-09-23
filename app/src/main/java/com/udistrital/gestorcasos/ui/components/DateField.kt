package com.udistrital.gestorcasos.ui.components

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Campo de solo lectura que, al pulsarlo, abre el selector de fecha nativo de Android
 * (DatePickerDialog) y devuelve la fecha elegida como epoch millis.
 */
@Composable
fun DateField(
    label: String,
    dateMillis: Long,
    onDateSelected: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply { timeInMillis = dateMillis }
    val formatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dateMillis))

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val picked = Calendar.getInstance()
                        picked.set(year, month, dayOfMonth, 0, 0, 0)
                        picked.set(Calendar.MILLISECOND, 0)
                        onDateSelected(picked.timeInMillis)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
    ) {
        OutlinedTextField(
            value = formatted,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
