package com.example.healthy_diagnosis.infrastructure.repositories

import com.example.healthy_diagnosis.data.datasources.local.PhysicianDao
import com.example.healthy_diagnosis.data.datasources.remote.PhysicianApiService
import com.example.healthy_diagnosis.data.models.PhysicianEntity
import com.example.healthy_diagnosis.domain.repositories.PhysicianRepository
import javax.inject.Inject

class PhysicianRepositoryImpl @Inject constructor(
    private val physicianDao: PhysicianDao,
    private val physicianApiService: PhysicianApiService
): PhysicianRepository {
    override suspend fun insertPhysician(physicianEntity: PhysicianEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPhysician(): List<PhysicianEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePhysician(id: Int) {
        TODO("Not yet implemented")
    }
}