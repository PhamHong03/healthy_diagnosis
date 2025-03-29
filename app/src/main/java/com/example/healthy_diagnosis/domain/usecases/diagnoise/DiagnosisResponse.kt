package com.example.healthy_diagnosis.domain.usecases.diagnoise

data class DiagnosisResponse(
    val imageId: Int,
    val diseaseId: Int,
    val diseaseName: String
)
