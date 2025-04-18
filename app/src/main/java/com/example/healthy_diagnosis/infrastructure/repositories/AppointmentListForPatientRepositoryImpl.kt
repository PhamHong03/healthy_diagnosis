package com.example.healthy_diagnosis.infrastructure.repositories

import com.example.healthy_diagnosis.data.datasources.local.PatientDetailsDao
import com.example.healthy_diagnosis.domain.repositories.AppointmentListForPatientRepository
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentDetails
import com.example.healthy_diagnosis.domain.usecases.result.DiagnosisFullInfo

class AppointmentListForPatientRepositoryImpl (
    private val patientDetailsDao: PatientDetailsDao
) : AppointmentListForPatientRepository {
    override suspend fun getAllAppointmentByPatientId(patient_id: Int): List<AppointmentDetails> {
        return patientDetailsDao.getAllAppointmentByPatientId(patient_id)
    }

}

