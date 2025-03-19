package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.MedicalHistoryEntity

interface MedicalHistoryRepository {

    suspend fun getAllMedicalHistory(): List<MedicalHistoryEntity>
}