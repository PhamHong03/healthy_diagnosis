package com.example.healthy_diagnosis.data.datasources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.healthy_diagnosis.data.models.AccountEntity
import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.data.models.PhysicianEntity

@Database(
    entities = [
        AccountEntity::class,
        EducationEntity::class,
        PhysicianEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDAO
    abstract fun educationDao(): EducationDao
    abstract fun physicianDao(): PhysicianDao

    companion object {
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
    }
}