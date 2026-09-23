package co.edu.udistrital.parcial01_pc.Data.Local.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import co.edu.udistrital.parcial01_pc.Data.Local.Entity.CaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCase(caseEntity: CaseEntity)

    @Update
    suspend fun updateCase(caseEntity: CaseEntity)

    @Delete
    suspend fun deleteCase(caseEntity: CaseEntity)

    @Query("SELECT * FROM cases")
    fun getAllCases(): Flow<List<CaseEntity>>

    @Query("SELECT * FROM cases WHERE title LIKE '%' || :query || '%'")
    fun searchCases(query: String): Flow<List<CaseEntity>>

    @Query("SELECT * FROM cases WHERE id = :caseId")
    suspend fun getCaseById(caseId: Int): CaseEntity?
}