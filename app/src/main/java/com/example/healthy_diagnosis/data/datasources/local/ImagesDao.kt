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

    @Query("SELECT * FROM images")
    suspend fun getAllImagesFormAppointment(appointmentFormEntity: AppointmentFormEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imagesEntity: ImagesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllImages(images: List<ImagesEntity>)

    @Query("UPDATE images SET diseases_id = :diseaseId WHERE id = :imageId")
    suspend fun updateDiseaseId(imageId: Int, diseaseId: Int)


}