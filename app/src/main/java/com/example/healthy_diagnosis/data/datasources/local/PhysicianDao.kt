package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.PhysicianEntity

@Dao
interface PhysicianDao {

    @Query("SELECT * FROM physicians")
    suspend fun getAllPhysician(): List<PhysicianEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhysician(physicianEntity: PhysicianEntity)

    @Query("DELETE FROM physicians WHERE id = :id")
    suspend fun deletePhysician(id: Int)

}