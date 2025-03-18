package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.PatientEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PatientApiService {
    @GET("patients")
    suspend fun getAllPatients(): List<PatientEntity>

    @POST("patients")
    suspend fun insertPatient(patientEntity: PatientEntity): Response<Void>
    @PUT("patients/{id}")
    suspend fun updatePatient(@Path("id") id: Int, @Body patientEntity: PatientEntity): Response<Void>

    @DELETE("patients/{id}")
    suspend fun deletePatient(@Path("id") id: Int): Response<Void>
}