package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.EducationEntity
import com.example.healthy_diagnosis.data.models.PhysicianEntity
import com.example.healthy_diagnosis.data.models.SpecializationEntity

interface PhysicianRepository {

    suspend fun insertPhysician(physicianEntity: PhysicianEntity)

    suspend fun getAllPhysician(): List<PhysicianEntity>

    suspend fun deletePhysician(id: Int)

    suspend fun getPhysicianByAccountId(accountId: Int): PhysicianEntity?

    suspend fun getPhysicianIdByAccountId(accountId: Int): Int?

    suspend fun checkPhysicianExists(accountId: Int): Boolean
}