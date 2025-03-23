package com.example.healthy_diagnosis.data.datasources.remote

import androidx.room.Query
import com.example.healthy_diagnosis.data.models.PhysicianEntity
import com.example.healthy_diagnosis.domain.usecases.patient.PatientWithApplicationDate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface PhysicianApiService {

    @GET("physicians")
    suspend fun getAllPhysician(): List<PhysicianEntity>

    @POST("physicians")
    suspend fun insertPhysician(@Body physicianEntity: PhysicianEntity): Response<Void>

    @PUT("physicians/{id}")
    suspend fun updatePhysician(@Path("id") id: Int, @Body physicianEntity: PhysicianEntity): Response<Void>

    @DELETE("physicians/{id}")
    suspend fun deletePhysican(@Path("id") id: Int): Response<Void>

    @GET("physicians/account/{account_id}")
    suspend fun getPhysicianByAccountId(@Path("account_id") acocuntId: Int): PhysicianEntity?

    @GET("physician/{physician_id}/patients")
    suspend fun getPatientsByPhysician(@Path("physician_id") physicianId: Int): List<PatientWithApplicationDate>

}