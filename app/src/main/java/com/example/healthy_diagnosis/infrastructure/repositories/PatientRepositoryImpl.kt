package com.example.healthy_diagnosis.infrastructure.repositories

import com.example.healthy_diagnosis.data.datasources.local.PatientDao
import com.example.healthy_diagnosis.data.datasources.remote.PatientApiService
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.domain.repositories.PatientRepository

class PatientRepositoryImpl(
    private val patientDao: PatientDao,
    private val patientApiService: PatientApiService
) : PatientRepository{
    override suspend fun insertPatient(patientRepository: PatientRepository) {

    }

    override suspend fun getAllPatient(): List<PatientEntity> {
        return emptyList()
    }

    override suspend fun deletePatient(id: Int) {

    }
}