package com.udistrital.gestorcasos.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.udistrital.gestorcasos.GestorCasosApplication
import com.udistrital.gestorcasos.ui.screens.caseform.CaseFormViewModel
import com.udistrital.gestorcasos.ui.screens.caselist.CaseListViewModel
import com.udistrital.gestorcasos.ui.screens.casedetail.CaseDetailViewModel
import com.udistrital.gestorcasos.ui.screens.home.HomeViewModel

/**
 * Fábrica central de ViewModels de la app (sin librerías de DI externas).
 * Cada pantalla registra aquí su propio initializer.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(gestorCasosApplication().repository) }
        initializer { CaseListViewModel(gestorCasosApplication().repository) }
        initializer { CaseFormViewModel(gestorCasosApplication().repository) }
        initializer { CaseDetailViewModel(gestorCasosApplication().repository) }
    }
}

/**
 * Obtiene la instancia de [GestorCasosApplication] desde las [CreationExtras] del ViewModel,
 * lo que permite acceder al repositorio compartido sin pasarlo manualmente por cada pantalla.
 */
fun CreationExtras.gestorCasosApplication(): GestorCasosApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GestorCasosApplication)
