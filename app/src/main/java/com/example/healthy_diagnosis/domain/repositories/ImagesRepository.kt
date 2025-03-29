package com.example.healthy_diagnosis.domain.repositories

import androidx.lifecycle.LiveData
import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.usecases.diagnoise.DiagnosisResponse
import java.io.File

interface ImagesRepository {

    suspend fun uploadImage(file: File, imageId: Int): DiagnosisResponse?
    suspend fun updateDiseaseId(imageId: Int, diseaseId: Int)
    fun getDiagnosisHistory(): LiveData<List<ImagesEntity>>
}
