package com.udistrital.gestorcasos.ui.screens.caselist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.gestorcasos.data.model.Case
import com.udistrital.gestorcasos.data.repository.CaseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

/**
 * Lógica del listado: mantiene el texto de búsqueda y, cada vez que cambia,
 * vuelve a consultar el repositorio (searchCases si hay texto, allCases si no).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CaseListViewModel(private val repository: CaseRepository) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val cases: StateFlow<List<Case>> = _query
        .flatMapLatest { q ->
            if (q.isBlank()) repository.allCases else repository.searchCases(q.trim())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }
}
