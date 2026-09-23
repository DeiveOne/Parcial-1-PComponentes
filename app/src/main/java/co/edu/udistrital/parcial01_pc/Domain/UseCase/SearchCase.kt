package co.edu.udistrital.parcial01_pc.Domain.UseCase

import co.edu.udistrital.parcial01_pc.Domain.Model.Case
import co.edu.udistrital.parcial01_pc.Data.Repository.CaseRepository
import kotlinx.coroutines.flow.Flow

class SearchCase(
    private val repository: CaseRepository
) {
    operator fun invoke(query: String): Flow<List<Case>> {

        if (query.isBlank()) {
            return repository.getAllCases()
        }

        return repository.searchCases(query)
    }
}