package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentDetails

interface AppointmentListForPatientRepository {
    suspend fun getAllAppointmentByPatientId(patient_id: Int): List<AppointmentDetails>

}