package com.journalist.casemanager.domain.model

data class Interview(
    val id: Int = 0,
    val caseId: Int,
    val intervieweeName: String,
    val dateMillis: Long,
    val findings: String
)