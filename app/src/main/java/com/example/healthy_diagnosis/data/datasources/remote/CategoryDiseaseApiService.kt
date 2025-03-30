package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.CategoryDiseaseEntity
import retrofit2.http.GET

interface CategoryDiseaseApiService {
    @GET("category_disease")
    suspend fun getAllCategoryDisease(): List<CategoryDiseaseEntity>
}