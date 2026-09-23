package co.edu.udistrital.parcial01_pc.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import co.edu.udistrital.parcial01_pc.Data.Local.DAO.CaseDao
import co.edu.udistrital.parcial01_pc.Data.Local.DAO.InterviewDao
import co.edu.udistrital.parcial01_pc.Data.Local.Entity.CaseEntity
import co.edu.udistrital.parcial01_pc.Data.Local.Entity.InterviewEntity

@Database(
    entities = [CaseEntity::class, InterviewEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CaseDatabase : RoomDatabase() {

    abstract fun caseDao(): CaseDao
    abstract fun interviewDao(): InterviewDao
}