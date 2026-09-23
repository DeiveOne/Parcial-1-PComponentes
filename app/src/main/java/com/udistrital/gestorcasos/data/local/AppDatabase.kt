package com.udistrital.gestorcasos.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udistrital.gestorcasos.data.model.Case
import com.udistrital.gestorcasos.data.model.Interview

@Database(entities = [Case::class, Interview::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun caseDao(): CaseDao
    abstract fun interviewDao(): InterviewDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gestor_casos_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
