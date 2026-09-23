package co.edu.udistrital.parcial01_pc.Domain.UseCase

import co.edu.udistrital.parcial01_pc.Domain.Model.Case
import co.edu.udistrital.parcial01_pc.Domain.RepositoryInterface.InterfaceRepository
import kotlinx.coroutines.flow.Flow

class GetCase(
    private val repository: InterfaceRepository
) {
    operator fun invoke(): Flow<List<Case>> {
        return repository.getAllCases()
    }
}