package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.ApplicationFormEntity

interface ApplicationFormRespository {

    suspend fun getAllApplicationForm(): List<ApplicationFormEntity>

    suspend fun insertApplicationForm(applicationFormEntity: ApplicationFormEntity)

    suspend fun deleteApplicationForm(id: Int)

}