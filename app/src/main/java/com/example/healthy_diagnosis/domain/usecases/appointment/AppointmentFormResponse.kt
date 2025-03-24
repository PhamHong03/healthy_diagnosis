package com.example.healthy_diagnosis.domain.usecases.appointment

import com.example.healthy_diagnosis.data.models.AppointmentFormEntity

data class AppointmentFormResponse(
    val id: Int,
    val description: String,
    val application_form_id: Int
)

fun AppointmentFormResponse.toEntity(): AppointmentFormEntity {
    return AppointmentFormEntity(
        id = this.id,
        description = this.description,
        application_form_id = this.application_form_id
    )
}


