package com.example.healthy_diagnosis.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.repositories.ImagesRepository
import com.example.healthy_diagnosis.domain.usecases.images.ImageResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository
): ViewModel() {

    private val _imagesList = MutableStateFlow<List<ImagesEntity>>(emptyList())
    val imagesList = _imagesList

    //Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun fetchImages(){
        viewModelScope.launch {
            try {
                _imagesList.value = imagesRepository.getAllImages()
            }catch (e: Exception) {
                Log.e("ImagesViewModel", "Error fetching patients: ${e}")
            }
        }
    }

    init {
        fetchImages()
    }

    fun uploadImages(image: MultipartBody.Part, physicianId: Int, appointmentId: Int, diseasesId: Int?) {
        viewModelScope.launch {
            try {
                val isUploaded = imagesRepository.uploadImage(image, physicianId, appointmentId, diseasesId)
                if (isUploaded) {
                    val newImage = ImagesEntity(
                        path_name = "your_image_path_here",
                        images_created = "current_timestamp_here",
                        physician_id = physicianId,
                        diseases_id = diseasesId,
                        appointment_id = appointmentId
                    )
                    imagesRepository.insertImages(listOf(newImage))
                    println("Upload và lưu vào Room thành công!")
                } else {
                    println("Upload thất bại!")
                }
            } catch (e: Exception) {
                println("Lỗi khi upload: ${e.message}")
            }
        }
    }



}