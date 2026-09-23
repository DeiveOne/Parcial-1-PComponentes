package com.udistrital.gestorcasos.ui.screens.caseform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udistrital.gestorcasos.data.model.Case
import com.udistrital.gestorcasos.data.model.CaseStatus
import com.udistrital.gestorcasos.data.repository.CaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Estado del formulario. El mismo formulario sirve para crear (caseId = 0) y
 * para editar (caseId > 0, isEditing = true).
 */
data class CaseFormUiState(
    val caseId: Long = 0L,
    val title: String = "",
    val description: String = "",
    val dateMillis: Long = System.currentTimeMillis(),
    val status: CaseStatus = CaseStatus.ABIERTO,
    val conclusion: String = "",
    val isEditing: Boolean = false,
    val titleError: Boolean = false,
    val saved: Boolean = false
)

class CaseFormViewModel(private val repository: CaseRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CaseFormUiState())
    val uiState: StateFlow<CaseFormUiState> = _uiState.asStateFlow()

    private var loadedCaseId: Long? = null

    /** @param caseId null o <= 0 significa "caso nuevo"; en otro caso, carga el caso a editar. */
    fun load(caseId: Long?) {
        if (loadedCaseId == caseId) return
        loadedCaseId = caseId

        if (caseId == null || caseId <= 0L) {
            _uiState.value = CaseFormUiState()
            return
        }
        viewModelScope.launch {
            repository.getCaseById(caseId).collect { case ->
                if (case != null) {
                    _uiState.value = CaseFormUiState(
                        caseId = case.id,
                        title = case.title,
                        description = case.description,
                        dateMillis = case.date,
                        status = case.status,
                        conclusion = case.conclusion ?: "",
                        isEditing = true
                    )
                }
            }
        }
    }

    fun onTitleChange(value: String) {
        _uiState.value = _uiState.value.copy(title = value, titleError = false)
    }

    fun onDescriptionChange(value: String) {
        _uiState.value = _uiState.value.copy(description = value)
    }

    fun onDateChange(value: Long) {
        _uiState.value = _uiState.value.copy(dateMillis = value)
    }

    fun onStatusChange(value: CaseStatus) {
        _uiState.value = _uiState.value.copy(status = value)
    }

    fun onConclusionChange(value: String) {
        _uiState.value = _uiState.value.copy(conclusion = value)
    }

    fun save() {
        val current = _uiState.value
        if (current.title.isBlank()) {
            _uiState.value = current.copy(titleError = true)
            return
        }
        viewModelScope.launch {
            val case = Case(
                id = current.caseId,
                title = current.title.trim(),
                description = current.description.trim(),
                date = current.dateMillis,
                status = current.status,
                conclusion = current.conclusion.trim().ifBlank { null }
            )
            if (current.isEditing) repository.updateCase(case) else repository.saveCase(case)
            _uiState.value = current.copy(saved = true)
        }
    }
}
