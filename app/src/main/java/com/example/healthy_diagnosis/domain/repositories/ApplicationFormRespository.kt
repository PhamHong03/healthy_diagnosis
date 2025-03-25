package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.ApplicationFormEntity
import com.example.healthy_diagnosis.data.models.PatientEntity

interface ApplicationFormRespository {

    suspend fun getAllApplicationForm(): List<ApplicationFormEntity>

    suspend fun insertApplicationForm(applicationFormEntity: ApplicationFormEntity)

    suspend fun deleteApplicationForm(id: Int)

    suspend fun getPatientByApplicationFormId(applicationFormId: Int): PatientEntity?

}