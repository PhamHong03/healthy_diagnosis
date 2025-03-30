package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthy_diagnosis.data.models.AppointmentFormEntity
import com.example.healthy_diagnosis.data.models.ImagesEntity
import okhttp3.Response

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImagesEntity>)

    @Query("SELECT * FROM images")
    suspend fun getAllImages(): List<ImagesEntity>

    @Query("DELETE FROM images")
    suspend fun deleteAllImages()


}