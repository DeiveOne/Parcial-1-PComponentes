package com.journalist.casemanager.domain.repository

import com.journalist.casemanager.domain.model.Case
import com.journalist.casemanager.domain.model.Interview
import kotlinx.coroutines.flow.Flow

interface CaseRepository {

    fun getAllCases(): Flow<List<Case>>

    fun searchCases(query: String): Flow<List<Case>>

    suspend fun insertCase(case: Case)

    suspend fun deleteCase(case: Case)

    suspend fun getCaseById(caseId: Int): Case?

    suspend fun updateCase(case: Case)

    fun getInterviewsForCase(caseId: Int): Flow<List<Interview>>

    suspend fun insertInterview(interview: Interview)
}