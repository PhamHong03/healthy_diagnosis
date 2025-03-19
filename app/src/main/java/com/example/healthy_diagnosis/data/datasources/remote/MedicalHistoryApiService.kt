package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.MedicalHistoryEntity
import retrofit2.http.GET

interface MedicalHistoryApiService {

    @GET("medical_histories")
    suspend fun getAllMedicalHistory(): List<MedicalHistoryEntity>
}