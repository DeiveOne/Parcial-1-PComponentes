package com.udistrital.gestorcasos.data.repository

import com.udistrital.gestorcasos.data.local.CaseDao
import com.udistrital.gestorcasos.data.local.InterviewDao
import com.udistrital.gestorcasos.data.model.Case
import com.udistrital.gestorcasos.data.model.Interview
import kotlinx.coroutines.flow.Flow

/**
 * Punto único de acceso a los datos de casos y entrevistas.
 * Aísla a las capas superiores (ViewModel / UI) de los detalles de Room,
 * cumpliendo la separación interfaz / lógica / persistencia pedida en el enunciado.
 */
class CaseRepository(
    private val caseDao: CaseDao,
    private val interviewDao: InterviewDao
) {
    // ---- Casos ----
    val allCases: Flow<List<Case>> = caseDao.getAll()

    fun searchCases(query: String): Flow<List<Case>> = caseDao.search(query)

    fun getCaseById(caseId: Long): Flow<Case?> = caseDao.getById(caseId)

    suspend fun saveCase(case: Case): Long = caseDao.insert(case)

    suspend fun updateCase(case: Case) = caseDao.update(case)

    suspend fun deleteCase(case: Case) = caseDao.delete(case)

    // ---- Entrevistas ----
    fun getInterviewsForCase(caseId: Long): Flow<List<Interview>> =
        interviewDao.getByCaseId(caseId)

    suspend fun saveInterview(interview: Interview): Long = interviewDao.insert(interview)

    suspend fun updateInterview(interview: Interview) = interviewDao.update(interview)

    suspend fun deleteInterview(interview: Interview) = interviewDao.delete(interview)
}
