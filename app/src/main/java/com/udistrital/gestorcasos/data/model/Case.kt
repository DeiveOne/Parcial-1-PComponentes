package com.udistrital.gestorcasos.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa un caso criminal registrado por el periodista.
 *
 * @param date fecha del caso, almacenada como epoch millis (System.currentTimeMillis()).
 * @param conclusion conclusión final del caso; queda en null mientras el caso sigue
 *                    abierto o en investigación, y se completa al cerrarlo.
 */
@Entity(tableName = "cases")
data class Case(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val description: String,
    val date: Long,
    val status: CaseStatus = CaseStatus.ABIERTO,
    val conclusion: String? = null
)
