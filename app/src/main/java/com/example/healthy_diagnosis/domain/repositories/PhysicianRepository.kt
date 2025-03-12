package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.PhysicianEntity

interface PhysicianRepository {

    suspend fun insertPhysician(physicianEntity: PhysicianEntity)

    suspend fun getAllPhysician(): List<PhysicianEntity>

    suspend fun deletePhysician(id: Int)
}