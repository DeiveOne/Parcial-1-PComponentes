package co.edu.udistrital.parcial01_pc.Domain.UseCase

import co.edu.udistrital.parcial01_pc.Domain.Model.Case
import co.edu.udistrital.parcial01_pc.Data.Repository.CaseRepository

class CreateCase(
    private val repository: CaseRepository
) {
    suspend operator fun invoke(case: Case) {

        if (case.title.isBlank()) {
            throw IllegalArgumentException("El título del caso no puede estar vacío.")
        }

        // Regla de negocio 2: El caso debe tener una descripción
        if (case.description.isBlank()) {
            throw IllegalArgumentException("La descripción del caso es obligatoria.")
        }


        repository.insertCase(case)
    }
}