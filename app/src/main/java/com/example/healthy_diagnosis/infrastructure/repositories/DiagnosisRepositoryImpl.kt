package com.example.healthy_diagnosis.infrastructure.repositories

import com.example.healthy_diagnosis.data.datasources.local.DiagnosisDao
import com.example.healthy_diagnosis.domain.repositories.DiagnosisRepository
import com.example.healthy_diagnosis.domain.usecases.result.DiagnosisFullInfo

class DiagnosisRepositoryImpl(
    private val diagnosisDao: DiagnosisDao
) : DiagnosisRepository {
    override suspend fun getDiagnosisFullInfo(appointmentId: Int): DiagnosisFullInfo? {
        return diagnosisDao.getDiagnosisFullInfo(appointmentId)
    }
}
