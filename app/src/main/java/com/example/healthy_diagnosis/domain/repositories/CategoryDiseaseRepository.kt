package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.CategoryDiseaseEntity

interface CategoryDiseaseRepository {

    suspend fun getAllCategoryDisease(): List<CategoryDiseaseEntity>
}