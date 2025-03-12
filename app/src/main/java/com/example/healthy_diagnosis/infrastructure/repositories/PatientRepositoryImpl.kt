package com.example.healthy_diagnosis.infrastructure.repositories

import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.domain.repositories.PatientRepository

class PatientRepositoryImpl : PatientRepository{
    override suspend fun insertPatient(patientRepository: PatientRepository) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPatient(): List<PatientEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePatient(id: Int) {
        TODO("Not yet implemented")
    }
}