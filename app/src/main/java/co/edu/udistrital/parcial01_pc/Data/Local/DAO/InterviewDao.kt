package co.edu.udistrital.parcial01_pc.Data.Local.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.udistrital.parcial01_pc.Data.Local.Entity.InterviewEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInterview(interviewEntity: InterviewEntity)

    @Query("SELECT * FROM interviews WHERE caseId = :caseId")
    fun getInterviewsByCaseId(caseId: Int): Flow<List<InterviewEntity>>
}