package com.example.healthy_diagnosis.domain.usecases.patient

data class PatientWithApplicationDate(
    val patient_id: Int,
    val patient_name: String,
    val application_form_date: String
)
