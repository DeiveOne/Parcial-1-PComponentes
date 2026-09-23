package com.udistrital.gestorcasos

import android.app.Application
import com.udistrital.gestorcasos.data.local.AppDatabase
import com.udistrital.gestorcasos.data.repository.CaseRepository

/**
 * Clase Application: crea una única instancia de la base de datos y del repositorio
 * para que estén disponibles en toda la app (inyección de dependencias manual,
 * sin librerías externas como Hilt, para mantener el proyecto simple).
 */
class GestorCasosApplication : Application() {

    private val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    val repository: CaseRepository by lazy {
        CaseRepository(database.caseDao(), database.interviewDao())
    }
}
