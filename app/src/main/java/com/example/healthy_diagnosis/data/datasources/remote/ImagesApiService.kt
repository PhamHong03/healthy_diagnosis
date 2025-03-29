package com.example.healthy_diagnosis.data.datasources.remote

import com.example.healthy_diagnosis.data.models.ImagesEntity
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ImagesApiService {

    @GET("images")
    suspend fun getAllImages(): List<ImagesEntity>

    @POST("images")
    suspend fun insertImages(@Body imagesEntity: ImagesEntity): retrofit2.Response<Void>



}