package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.EducationEntity

@Dao
interface EducationDao {

    @Query("SELECT * FROM educations")
    suspend fun getAllEducation(): List<EducationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEducation(educationEntity: EducationEntity)

    @Query("DELETE FROM educations WHERE id = :id")
    suspend fun deleteEducation(id: Int)

}