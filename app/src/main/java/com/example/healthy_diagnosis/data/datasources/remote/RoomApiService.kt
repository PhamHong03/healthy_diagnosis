package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.RoomEntity
import retrofit2.http.GET

interface RoomApiService {

    @GET("rooms")
    suspend fun getAllRooms(): List<RoomEntity>
}