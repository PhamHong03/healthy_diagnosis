package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.SpecializationEntity

@Dao
interface SpecializationDao {
    @Query("SELECT * FROM  specializations")
    suspend fun getAllSpecialization(): List<SpecializationEntity>
}