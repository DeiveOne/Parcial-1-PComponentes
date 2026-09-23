package co.edu.udistrital.parcial01_pc.Domain.RepositoryInterface

import co.edu.udistrital.parcial01_pc.Domain.Model.Case
import co.edu.udistrital.parcial01_pc.Domain.Model.Interview
import kotlinx.coroutines.flow.Flow

interface InterfaceRepository {

    fun getAllCases(): Flow<List<Case>>

    fun searchCases(query: String): Flow<List<Case>>

    suspend fun insertCase(case: Case)

    suspend fun deleteCase(case: Case)

    suspend fun getCaseById(caseId: Int): Case?

    suspend fun updateCase(case: Case)

    fun getInterviewsForCase(caseId: Int): Flow<List<Interview>>

    suspend fun insertInterview(interview: Interview)
}