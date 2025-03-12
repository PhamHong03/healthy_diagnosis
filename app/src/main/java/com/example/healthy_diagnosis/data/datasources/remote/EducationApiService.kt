package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.EducationEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EducationApiService {

    @GET("educations")
    suspend fun getAllEducations(): List<EducationEntity>

    @POST("educations")
    suspend fun insertEducation(@Body educationEntity: EducationEntity): Response<Void>

    @PUT("educations/{id}")
    suspend fun updateEducation( @Path("id") id : Int, @Body educationEntity: EducationEntity) : Response<Void>

    @DELETE("educations/{id}")
    suspend fun deleteEducation(@Path("id") id: Int): Response<Void>

}