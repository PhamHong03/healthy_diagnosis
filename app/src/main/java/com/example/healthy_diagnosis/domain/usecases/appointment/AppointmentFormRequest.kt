package com.example.healthy_diagnosis.domain.usecases.appointment

data class AppointmentFormRequest(
    val description: String,
    val application_form_id: Int
)
