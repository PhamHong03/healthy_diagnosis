package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.MedicalHistoryEntity
import com.example.healthy_diagnosis.data.models.PatientEntity

interface MedicalHistoryRepository {

    suspend fun getAllMedicalHistory(): List<MedicalHistoryEntity>
}