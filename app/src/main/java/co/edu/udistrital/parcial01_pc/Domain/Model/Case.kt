package co.edu.udistrital.parcial01_pc.Domain.Model

data class Case(
    val id: Int = 0,
    val title: String,
    val description: String,
    val dateMillis: Long,
    val status: CaseStatus = CaseStatus.OPEN,
    val conclusion: String? = null
)