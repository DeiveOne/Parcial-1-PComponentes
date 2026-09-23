package co.edu.udistrital.parcial01_pc.Domain.Model

data class Interview(
    val id: Int = 0,
    val caseId: Int,
    val intervieweeName: String,
    val dateMillis: Long,
    val findings: String
)