package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.datasources.local.AppointmentFormDao
import com.example.healthy_diagnosis.data.datasources.remote.AppointmentFormApiService
import com.example.healthy_diagnosis.data.models.AppointmentFormEntity
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormResponse
import javax.inject.Inject

interface AppointmentFormRepository {

    suspend fun getAllAppointmentForms(): List<AppointmentFormEntity>

    suspend fun insertAppointmentForm(appointmentFormEntity: AppointmentFormEntity)

    suspend fun getAppointmentFormById(id: Int): AppointmentFormEntity?
    suspend fun getLatestAppointmentId(): Int?
}