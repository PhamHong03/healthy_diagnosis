package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.EducationEntity

interface EducationRepository {
    suspend fun insertEducation(educationEntity: EducationEntity)

    suspend fun getAllEducation() : List<EducationEntity>

    suspend fun deleteEducation(id: Int)
}