package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.DiseaseEntity
import okhttp3.Response

interface DiseaseRepository {

    suspend fun getAllDisease(): List<DiseaseEntity>

    suspend fun getDiseaseById(id: Int): DiseaseEntity?
}