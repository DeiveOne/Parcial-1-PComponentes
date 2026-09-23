package com.udistrital.gestorcasos.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.udistrital.gestorcasos.data.model.Interview
import kotlinx.coroutines.flow.Flow

@Dao
interface InterviewDao {

    @Insert
    suspend fun insert(interview: Interview): Long

    @Update
    suspend fun update(interview: Interview)

    @Delete
    suspend fun delete(interview: Interview)

    @Query("SELECT * FROM interviews WHERE caseId = :caseId ORDER BY date DESC")
    fun getByCaseId(caseId: Long): Flow<List<Interview>>
}
