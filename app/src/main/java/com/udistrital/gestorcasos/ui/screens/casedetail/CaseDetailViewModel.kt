package com.udistrital.gestorcasos.ui.screens.casedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.gestorcasos.data.model.Case
import com.udistrital.gestorcasos.data.model.CaseStatus
import com.udistrital.gestorcasos.data.model.Interview
import com.udistrital.gestorcasos.data.repository.CaseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CaseDetailUiState(
    val case: Case? = null,
    val interviews: List<Interview> = emptyList()
)

/**
 * Lógica de la pantalla de detalle: combina el caso y sus entrevistas en un solo
 * estado, y expone las acciones de agregar entrevista, cambiar/cerrar estado y eliminar.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CaseDetailViewModel(private val repository: CaseRepository) : ViewModel() {

    private val _caseId = MutableStateFlow(0L)

    val uiState: StateFlow<CaseDetailUiState> = _caseId
        .flatMapLatest { id ->
            if (id <= 0L) {
                flowOf(CaseDetailUiState())
            } else {
                combine(
                    repository.getCaseById(id),
                    repository.getInterviewsForCase(id)
                ) { case, interviews ->
                    CaseDetailUiState(case = case, interviews = interviews)
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CaseDetailUiState()
        )

    private val _deleted = MutableStateFlow(false)
    val deleted: StateFlow<Boolean> = _deleted.asStateFlow()

    fun load(caseId: Long) {
        _caseId.value = caseId
    }

    fun addInterview(intervieweeName: String, dateMillis: Long, findings: String) {
        val currentCase = uiState.value.case ?: return
        if (intervieweeName.isBlank() || findings.isBlank()) return
        viewModelScope.launch {
            repository.saveInterview(
                Interview(
                    caseId = currentCase.id,
                    intervieweeName = intervieweeName.trim(),
                    date = dateMillis,
                    findings = findings.trim()
                )
            )
        }
    }

    fun updateStatus(newStatus: CaseStatus, conclusion: String? = null) {
        val currentCase = uiState.value.case ?: return
        viewModelScope.launch {
            repository.updateCase(
                currentCase.copy(
                    status = newStatus,
                    conclusion = conclusion ?: currentCase.conclusion
                )
            )
        }
    }

    fun deleteCase() {
        val currentCase = uiState.value.case ?: return
        viewModelScope.launch {
            repository.deleteCase(currentCase)
            _deleted.value = true
        }
    }
}
