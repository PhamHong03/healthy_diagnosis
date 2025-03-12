package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.PatientEntity

interface PatientRepository {

    suspend fun insertPatient(patientRepository: PatientRepository)

    suspend fun getAllPatient(): List<PatientEntity>

    suspend fun deletePatient(id: Int)
}