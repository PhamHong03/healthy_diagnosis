package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.ApplicationFormEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApplicationFormApiService {

    @GET("/application-forms")
    suspend fun getAllApplicationForms(): Response<List<ApplicationFormEntity>>

    @POST("application-forms")
    suspend fun insertApplicationForm(@Body applicationFormEntity: ApplicationFormEntity): Response<Void>

    @PUT("application-forms/{id}")
    suspend fun updateApplication(@Path("id") id: Int, @Body applicationFormEntity: ApplicationFormEntity):Response<Void>

    @DELETE("application-forms/{id}")
    suspend fun deleteApplication(@Path("id") id: Int): Response<Void>


}