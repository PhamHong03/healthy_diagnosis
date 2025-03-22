package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.PatientEntity

interface PatientRepository {

    suspend fun insertPatient(patientEntity: PatientEntity)

    suspend fun getAllPatient(): List<PatientEntity>

    suspend fun deletePatient(id: Int)

    suspend fun getPatientByAccountId(accountId: Int): PatientEntity?

//    suspend fun getPatientById(patientId: Int): PatientEntity
}