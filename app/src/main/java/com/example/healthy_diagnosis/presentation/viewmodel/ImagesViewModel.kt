package com.example.healthy_diagnosis.presentation.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthy_diagnosis.data.models.ImagesEntity
import com.example.healthy_diagnosis.domain.repositories.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.example.healthy_diagnosis.data.datasources.remote.ImagesApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val imagesRepository: ImagesRepository,
    private val imagesApiService: ImagesApiService,
    private val application: Application
): ViewModel() {

    private val _imagesList = MutableStateFlow<List<ImagesEntity>>(emptyList())
    val imagesList = _imagesList

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _imageUpdateStatus = MutableLiveData<Boolean>()
    val imageUpdateStatus: LiveData<Boolean> get() = _imageUpdateStatus

//    fun uploadImage(image: MultipartBody.Part, physicianId: Int, appointmentId: Int, diseasesId: Int?) {
//        viewModelScope.launch {
//            val isUploaded = imagesRepository.uploadImage(image, physicianId, appointmentId, diseasesId)
//            _imageUpdateStatus.value = isUploaded
//        }
//    }

//    fun uploadImage(imageUri: Uri, physicianId: Int, appointmentId: Int, diseasesId: Int?) {
//        val context = application.applicationContext
//
//        val filePath = getRealPathFromURI(imageUri, context) ?: return
//        val file = File(filePath)
//
//        val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
//
//        val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
//
//        viewModelScope.launch {
//            try {
//                val response = imagesApiService.uploadImage(part, physicianId, appointmentId, diseasesId)
//                if (response.isSuccessful) {
//                    Log.d("Upload", "Image uploaded successfully")
//                } else {
//                    Log.e("Upload", "Failed to upload image")
//                }
//            } catch (e: Exception) {
//                Log.e("Upload", "Error: ${e.message}")
//            }
//        }
//    }

    fun uploadImage(uri: Uri, physicianId: Int, appointmentId: Int, diseasesId: Int?) {
        val context = application.applicationContext

        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val byteArray = inputStream?.readBytes()

                if (byteArray != null) {
//                    val requestBody = byteArray.toRequestBody("image/*".toMediaTypeOrNull())
                    val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())

                    val part = MultipartBody.Part.createFormData("file", "image.jpg", requestBody)

                    val response = imagesApiService.uploadImage(part, physicianId, appointmentId, diseasesId)
                    if (response.isSuccessful) {
                        Log.d("Upload", "Image uploaded successfully")
                    } else {
                        val baseUrl = "http://172.16.43.219:5000"
                        Log.d("Upload", "API Base URL: $baseUrl")
                        Log.e("Upload", "Failed to upload image: ${response.code()} - ${response.message()}")
                    }
                } else {
                    Log.e("Upload", "InputStream is null")
                }
            } catch (e: Exception) {
                Log.e("Upload", "Upload failed: ${e.message}", e)
            }
        }
    }



    fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        }
        return null
    }
    fun updateDiseaseId(imageId: Int, newDiseaseId: Int) {
        viewModelScope.launch {
            try {
                val image = imagesRepository.getImageById(imageId)
                image?.let {
                    val updatedImage = it.copy(diseases_id = newDiseaseId)
                    imagesRepository.updateImageEntity(updatedImage)
                    _imageUpdateStatus.value = true
                } ?: run {
                    _imageUpdateStatus.value = false
                }
            } catch (e: Exception) {
                Log.e("ImagesViewModel", "Lỗi khi cập nhật disease ID: ${e.message}")
                _imageUpdateStatus.value = false
            }
        }
    }


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
                        images_path = "your_image_path_here",
                        created_at = "current_timestamp_here",
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