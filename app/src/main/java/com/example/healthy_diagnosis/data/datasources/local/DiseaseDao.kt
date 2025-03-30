package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.CategoryDiseaseEntity
import com.example.healthy_diagnosis.data.models.DiseaseEntity
import retrofit2.http.GET
import retrofit2.http.POST

@Dao
interface DiseaseDao {

    @Query("SELECT * FROM diseases WHERE id = :id")
    suspend fun getLocalDiseaseById(id: Int): DiseaseEntity?

    @Query("SELECT * FROM diseases")
    suspend fun getAllDisease(): List<DiseaseEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(diseases: List<DiseaseEntity>)

}