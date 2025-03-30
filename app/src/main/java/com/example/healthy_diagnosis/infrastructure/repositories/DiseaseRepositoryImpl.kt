package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.DiseaseDao
import com.example.healthy_diagnosis.data.datasources.remote.DiseaseApiService
import com.example.healthy_diagnosis.data.models.DiseaseEntity
import com.example.healthy_diagnosis.domain.repositories.DiseaseRepository
import javax.inject.Inject

class DiseaseRepositoryImpl @Inject constructor(
    private val diseaseDao: DiseaseDao,
    private val diseaseApiService: DiseaseApiService
): DiseaseRepository{
    override suspend fun getAllDisease(): List<DiseaseEntity> {
        return kotlin.runCatching {
            val diseases = diseaseApiService.getAllDisease()
            diseaseDao.insertAll(diseases)
            diseases
        }.getOrElse {
            Log.e("RoomRepo", "Lỗi kết nối API: ${it.message}")
            diseaseDao.getAllDisease()
        }
    }

    override suspend fun getDiseaseById(id: Int): DiseaseEntity {
        return diseaseDao.getLocalDiseaseById(id) ?: kotlin.runCatching {
            val response = diseaseApiService.getDiseaseById(id)
            if (response.isSuccessful) {
                response.body()?.also { diseaseDao.insertAll(listOf(it)) } ?: throw Exception("Không tìm thấy bệnh")
            } else {
                throw Exception("Lỗi API: ${response.code()}")
            }
        }.getOrElse {
            Log.e("DiseaseRepository", "Lỗi khi lấy disease by ID: ${it.message}")
            throw Exception("Không thể lấy bệnh")
        }
    }


}