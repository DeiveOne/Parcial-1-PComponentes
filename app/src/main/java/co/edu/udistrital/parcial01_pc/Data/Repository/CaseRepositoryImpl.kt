package co.edu.udistrital.parcial01_pc.Data.Repository

import co.edu.udistrital.parcial01_pc.Domain.Model.Case
import co.edu.udistrital.parcial01_pc.Domain.Model.CaseStatus
import co.edu.udistrital.parcial01_pc.Domain.Model.Interview
import co.edu.udistrital.parcial01_pc.Domain.RepositoryInterface.InterfaceRepository
import co.edu.udistrital.parcial01_pc.Data.Local.DAO.CaseDao
import co.edu.udistrital.parcial01_pc.Data.Local.DAO.InterviewDao
import co.edu.udistrital.parcial01_pc.Data.Local.Entity.CaseEntity
import co.edu.udistrital.parcial01_pc.Data.Local.Entity.InterviewEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CaseRepositoryImpl(
    private val caseDao: CaseDao,
    private val interviewDao: InterviewDao
) : InterfaceRepository {

    override fun getAllCases(): Flow<List<Case>> {
        return caseDao.getAllCases().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun searchCases(query: String): Flow<List<Case>> {
        return caseDao.searchCases(query).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertCase(case: Case) {
        caseDao.insertCase(case.toEntity())
    }

    override suspend fun deleteCase(case: Case) {
        caseDao.deleteCase(case.toEntity())
    }

    override suspend fun getCaseById(caseId: Int): Case? {
        return caseDao.getCaseById(caseId)?.toDomain()
    }

    override suspend fun updateCase(case: Case) {
        caseDao.updateCase(case.toEntity())
    }

    override fun getInterviewsForCase(caseId: Int): Flow<List<Interview>> {
        return interviewDao.getInterviewsByCaseId(caseId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertInterview(interview: Interview) {
        interviewDao.insertInterview(interview.toEntity())
    }
}

// ============================================================================
// Mappers (Convertidores entre Entidades de Room y Modelos de Dominio)
// ============================================================================

fun CaseEntity.toDomain() = Case(
    id = id,
    title = title,
    description = description,
    dateMillis = date.toLongOrNull() ?: 0L,
    status = try { CaseStatus.valueOf(status) } catch (e: Exception) { CaseStatus.OPEN },
    conclusion = conclusion
)

fun Case.toEntity() = CaseEntity(
    id = id,
    title = title,
    description = description,
    date = dateMillis.toString(),
    status = status.name,
    conclusion = conclusion
)

fun InterviewEntity.toDomain() = Interview(
    id = id,
    caseId = caseId,
    intervieweeName = interviewer,
    dateMillis = date.toLongOrNull() ?: 0L,
    findings = findings
)

fun Interview.toEntity() = InterviewEntity(
    id = id,
    caseId = caseId,
    interviewer = intervieweeName,
    date = dateMillis.toString(),
    findings = findings
)