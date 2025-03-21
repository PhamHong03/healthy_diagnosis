package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.MedicalHistoryDao
import com.example.healthy_diagnosis.data.datasources.remote.MedicalHistoryApiService
import com.example.healthy_diagnosis.data.models.MedicalHistoryEntity
import com.example.healthy_diagnosis.data.models.PatientEntity
import com.example.healthy_diagnosis.domain.repositories.MedicalHistoryRepository

class MedicalHistoryRepositoryImpl (
    private val medicalHistoryDao: MedicalHistoryDao,
    private val medicalHistoryApiService: MedicalHistoryApiService
): MedicalHistoryRepository{
    override suspend fun getAllMedicalHistory(): List<MedicalHistoryEntity> {
        return kotlin.runCatching {
            val medicalhistories = medicalHistoryApiService.getAllMedicalHistory()
            medicalHistoryDao.insertAll(medicalhistories)
            medicalhistories
        }.getOrElse {
            Log.e("MedicalHistoryRepo", "Lỗi kết nối API: ${it.message}")
            medicalHistoryDao.getAll()
        }
    }


}