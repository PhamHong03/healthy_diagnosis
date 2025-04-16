package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.domain.usecases.result.DiagnosisFullInfo

interface DiagnosisRepository {
    suspend fun getDiagnosisFullInfo(appointmentId: Int): DiagnosisFullInfo?
}