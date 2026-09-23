package com.udistrital.gestorcasos.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.udistrital.gestorcasos.data.model.Case
import kotlinx.coroutines.flow.Flow

@Dao
interface CaseDao {

    @Insert
    suspend fun insert(caseItem: Case): Long

    @Update
    suspend fun update(caseItem: Case)

    @Delete
    suspend fun delete(caseItem: Case)

    @Query("SELECT * FROM cases WHERE id = :caseId")
    fun getById(caseId: Long): Flow<Case?>

    @Query("SELECT * FROM cases ORDER BY date DESC")
    fun getAll(): Flow<List<Case>>

    /** Búsqueda usada por el listado de casos (por título). */
    @Query("SELECT * FROM cases WHERE title LIKE '%' || :query || '%' ORDER BY date DESC")
    fun search(query: String): Flow<List<Case>>
}
