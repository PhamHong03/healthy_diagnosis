package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.AppointmentFormEntity
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormResponse

@Dao
interface AppointmentFormDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAppointmentForm(appointmentFormEntity: AppointmentFormEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointmentForm(appointmentFormEntity: AppointmentFormEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAppointmentForm(appointments: List<AppointmentFormEntity>)

    @Query("SELECT * FROM appointment_forms WHERE id = :id LIMIT 1")
    suspend fun getAppointmentFormById(id: Int): AppointmentFormEntity?

    @Query("SELECT * FROM appointment_forms")
    suspend fun getAllAppointmentForms(): List<AppointmentFormEntity>

    @Query("SELECT * FROM appointment_forms WHERE application_form_id = :id")
    suspend fun getAppointmentFormsByApplicationFormId(id: Int): List<AppointmentFormEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM application_forms WHERE id = :applicationFormId)")
    suspend fun doesApplicationFormExist(applicationFormId: Int): Boolean

    @Query("SELECT id FROM appointment_forms ORDER BY id DESC LIMIT 1")
    fun getLatestAppointmentId(): Int?

}