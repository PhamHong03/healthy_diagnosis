package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.CategoryDiseaseEntity

@Dao
interface CategoryDiseaseDao {

    @Query("SELECT * FROM category_disease")
    suspend fun getAllCategoryDisease(): List<CategoryDiseaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(disease: List<CategoryDiseaseEntity>)
}