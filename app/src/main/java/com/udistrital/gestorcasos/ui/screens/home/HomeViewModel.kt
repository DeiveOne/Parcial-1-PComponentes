package com.udistrital.gestorcasos.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.gestorcasos.data.model.Case
import com.udistrital.gestorcasos.data.model.CaseStatus
import com.udistrital.gestorcasos.data.repository.CaseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Estado que consume la pantalla Home: totales por estado y los casos más recientes.
 * Separado en su propia data class para mantener la UI (HomeScreen) "tonta":
 * solo pinta lo que el ViewModel le entrega.
 */
data class HomeUiState(
    val totalCases: Int = 0,
    val openCases: Int = 0,
    val investigatingCases: Int = 0,
    val closedCases: Int = 0,
    val recentCases: List<Case> = emptyList()
)

class HomeViewModel(repository: CaseRepository) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = repository.allCases
        .map { cases -> cases.toHomeUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeUiState()
        )

    private fun List<Case>.toHomeUiState(): HomeUiState = HomeUiState(
        totalCases = size,
        openCases = count { it.status == CaseStatus.ABIERTO },
        investigatingCases = count { it.status == CaseStatus.EN_INVESTIGACION },
        closedCases = count { it.status == CaseStatus.CERRADO },
        recentCases = sortedByDescending { it.date }.take(5)
    )
}
