package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.data.models.SpecializationEntity
import retrofit2.Response
import retrofit2.http.GET

interface SpecializationApiService {
    @GET("specializations")
    suspend fun getSpecializations(): Response<List<SpecializationEntity>>

}