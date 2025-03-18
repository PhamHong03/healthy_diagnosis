package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.SpecializationEntity

@Dao
interface SpecializationDao {
    @Query("SELECT * FROM  specializations")
    suspend fun getAllSpecialization(): List<SpecializationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSpecializations(specializations: List<SpecializationEntity>)
}