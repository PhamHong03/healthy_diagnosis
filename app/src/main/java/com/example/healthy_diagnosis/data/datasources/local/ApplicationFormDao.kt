package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.ApplicationFormEntity
import com.example.healthy_diagnosis.data.models.PatientEntity

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

    @Query("""
        SELECT p.* FROM patients p
        INNER JOIN application_forms af ON p.id = af.patient_id
        WHERE af.id = :applicationFormId
    """)
    suspend fun getPatientByApplicationFormId(applicationFormId: Int): PatientEntity?



    @Query("SELECT * FROM application_forms")
    suspend fun getAllApplicationForm(): List<ApplicationFormEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllApplicationForm(applicationForms: List<ApplicationFormEntity>)

    @Query("DELETE FROM application_forms")
    suspend fun deleteAllApplicationForms()
}