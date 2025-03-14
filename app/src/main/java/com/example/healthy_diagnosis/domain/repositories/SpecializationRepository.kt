package com.example.healthy_diagnosis.domain.repositories


import com.example.healthy_diagnosis.data.models.SpecializationEntity

interface SpecializationRepository {

    suspend fun getSpecializations(): List<SpecializationEntity>
}