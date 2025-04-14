package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormRequest
import com.example.healthy_diagnosis.domain.usecases.appointment.AppointmentFormResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentFormApiService {

    @GET("appointment-forms")
    suspend fun getAllAppointmentForms(): List<AppointmentFormResponse>

    @GET("appointment_form/{id}")
    suspend fun getAppointmentFormById(
        @Path("id") applicationFormId: Int
    ): Response<AppointmentFormResponse>

    @POST("appointment-forms")
    suspend fun insertAppointmentForm(@Body request: AppointmentFormRequest): Response<AppointmentFormResponse>

}