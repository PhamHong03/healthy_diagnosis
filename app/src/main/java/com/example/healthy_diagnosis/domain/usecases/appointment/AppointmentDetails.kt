package com.example.healthy_diagnosis.domain.usecases.appointment

data class AppointmentDetails(
    val appointment_id: Int,
    val appointment_description: String?,
    val image_path: String?,
    val image_created_at: String?,
    val disease_name: String?,
    val disease_description: String?,
    val category_name: String?,
    val category_description: String?,
    val application_form_id: Int,
    val application_content: String?,
    val application_form_date: String?,
    val patient_id: Int,
    val patient_name: String?,
    val phone: String?,
    val gender: String?,
    val day_of_birth: String?
)
