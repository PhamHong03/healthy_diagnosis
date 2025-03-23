package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.PhysicianEntity

@Dao
interface PhysicianDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhysician(physicianEntity: PhysicianEntity)

    @Query("SELECT * FROM physicians WHERE id = :id LIMIT 1")
    suspend fun getPhysicianById(id: Int): PhysicianEntity?

    @Query("DELETE FROM physicians WHERE id = :id")
    suspend fun deletePhysician(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(physicians: List<PhysicianEntity>)

    @Query("DELETE FROM physicians")
    suspend fun deleteAll()

    @Query("SELECT * FROM physicians")
    suspend fun getAllPhysician(): List<PhysicianEntity>


    @Query("SELECT id FROM physicians WHERE account_id = :accountId LIMIT 1")
    suspend fun getPhysicianIdByAccountId(accountId: Int): Int?


}
