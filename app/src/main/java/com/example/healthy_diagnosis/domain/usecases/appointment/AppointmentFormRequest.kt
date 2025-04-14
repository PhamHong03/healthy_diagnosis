package com.example.healthy_diagnosis.domain.usecases.appointment

import com.example.healthy_diagnosis.data.models.AppointmentFormEntity

data class AppointmentFormRequest(
    val description: String,
    val application_form_id: Int
)

fun AppointmentFormRequest.toEntity(): AppointmentFormEntity {
    return AppointmentFormEntity(
        description = this.description,
        application_form_id = this.application_form_id
    )
}
