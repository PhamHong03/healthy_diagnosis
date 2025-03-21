package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.MedicalHistoryEntity
import com.example.healthy_diagnosis.data.models.PhysicianEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MedicalHistoryDao {

    @Query("SELECT * FROM medical_histories")
    suspend fun getAll(): List<MedicalHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(medicalhistories: List<MedicalHistoryEntity>)

    @Query("SELECT * FROM medical_histories")
    fun getAllMedicalHistory(): Flow<List<MedicalHistoryEntity>>
}