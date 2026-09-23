package com.udistrital.gestorcasos.data.local

import androidx.room.TypeConverter
import com.udistrital.gestorcasos.data.model.CaseStatus

/**
 * Conversores de tipo para que Room pueda persistir el enum [CaseStatus]
 * (Room solo entiende tipos primitivos/String de forma nativa).
 */
class Converters {
    @TypeConverter
    fun fromCaseStatus(status: CaseStatus): String = status.name

    @TypeConverter
    fun toCaseStatus(value: String): CaseStatus = CaseStatus.valueOf(value)
}
