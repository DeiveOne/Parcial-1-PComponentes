package com.udistrital.gestorcasos.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Representa una entrevista realizada dentro de un caso, junto con sus hallazgos
 * principales. Al eliminar un caso (onDelete = CASCADE) se eliminan también sus entrevistas.
 */
@Entity(
    tableName = "interviews",
    foreignKeys = [
        ForeignKey(
            entity = Case::class,
            parentColumns = ["id"],
            childColumns = ["caseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("caseId")]
)
data class Interview(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val caseId: Long,
    val intervieweeName: String,
    val date: Long,
    val findings: String
)
