package com.example.healthy_diagnosis.infrastructure.repositories

import android.util.Log
import com.example.healthy_diagnosis.data.datasources.local.RoomDao
import com.example.healthy_diagnosis.data.datasources.remote.RoomApiService
import com.example.healthy_diagnosis.data.models.RoomEntity
import com.example.healthy_diagnosis.domain.repositories.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: RoomDao,
    private val roomApiService: RoomApiService
): RoomRepository{
    override suspend fun getAllRooms(): List<RoomEntity> {
        return kotlin.runCatching {
            val rooms = roomApiService.getAllRooms()
            roomDao.insertAll(rooms)
            rooms
        }.getOrElse {
            Log.e("RoomRepo", "Lỗi kết nối API: ${it.message}")
            roomDao.getAllRooms()
        }
    }

}