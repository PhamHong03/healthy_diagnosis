package com.example.healthy_diagnosis.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.healthy_diagnosis.data.models.AppointmentFormEntity
import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.data.models.RoomEntity
import okhttp3.Response

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(images: List<ImagesEntity>)

    @Query("SELECT * FROM images")
    suspend fun getAllImages(): List<ImagesEntity>

    @Query("DELETE FROM images")
    suspend fun deleteAllImages()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(room: List<ImagesEntity>)

    @Update
    suspend fun update(imagesEntity: ImagesEntity)

}