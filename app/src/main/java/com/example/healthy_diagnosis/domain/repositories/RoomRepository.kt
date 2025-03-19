package com.example.healthy_diagnosis.domain.repositories

import com.example.healthy_diagnosis.data.models.RoomEntity

interface RoomRepository {

    suspend fun getAllRooms(): List<RoomEntity>
}