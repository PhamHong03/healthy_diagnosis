package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.ApplicationFormEntity

@Dao
interface ApplicationFormDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplicationForm(applicationFormEntity: ApplicationFormEntity)

    @Query("SELECT * FROM application_forms WHERE id = :id LIMIT 1")
    suspend fun getApplicationFormById(id: Int): ApplicationFormEntity?

    @Query("DELETE FROM application_forms WHERE id = :id")
    suspend fun deleteApplicationForm(id: Int)

    @Query("DELETE FROM application_forms")
    suspend fun deleteAll()



    @Query("SELECT * FROM application_forms")
    suspend fun getAllApplicationForm(): List<ApplicationFormEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllApplicationForm(applicationForms: List<ApplicationFormEntity>)

    @Query("DELETE FROM application_forms")
    suspend fun deleteAllApplicationForms()
}