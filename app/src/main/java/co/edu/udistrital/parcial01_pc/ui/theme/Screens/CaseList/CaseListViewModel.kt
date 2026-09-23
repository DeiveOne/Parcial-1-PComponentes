package co.edu.udistrital.parcial01_pc.ui.theme.Screens.CaseList

import androidx.lifecycle.ViewModel
import co.edu.udistrital.parcial01_pc.Domain.Model.Case
import co.edu.udistrital.parcial01_pc.Domain.Model.CaseStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CaseListViewModel : ViewModel() {


    private val mockCases = listOf(
        Case(1,
             "Robo en Usaquén",
             "Robo a mano armada en la calle 116",
             1695420000L,
             CaseStatus.OPEN,
             null),

        Case(2,
             "Fraude Bancario",
             "Desvío de fondos de la cuenta principal",
             1695000000L,
             CaseStatus.CLOSED,
             "Se encontró al culpable en auditoría"),

        Case(3,
             "Desaparición",
             "Persona vista por última vez en el parque",
             1695100000L,
             CaseStatus.OPEN,
             null)
    )


    private val _cases = MutableStateFlow<List<Case>>(mockCases)
    val cases: StateFlow<List<Case>> = _cases.asStateFlow()


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query


        if (query.isBlank()) {
            _cases.value = mockCases
        } else {
            _cases.value = mockCases.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
    }
}