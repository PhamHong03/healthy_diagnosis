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
import javax.inject.Inject
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.example.healthy_diagnosis.core.utils.prepareImagePart
import com.example.healthy_diagnosis.data.datasources.remote.ImagesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
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

    fun uploadImage(uri: Uri, physicianId: Int, appointmentId: Int, diseasesId: Int?) {
        val context = application.applicationContext
        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                if (inputStream == null) {
                    Log.e("Upload", "Không thể mở InputStream từ URI.")
                    return@launch
                }

                val filePart = prepareImagePart(context, uri)
                val physicianIdBody = physicianId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val appointmentIdBody = appointmentId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val diseasesIdBody = (diseasesId ?: 0).toString().toRequestBody("text/plain".toMediaTypeOrNull())

                Log.d("Upload", "physicianId=$physicianId, appointmentId=$appointmentId, diseasesId=${diseasesId ?: 0}")

                val isUploaded = imagesRepository.uploadImage(
                    image = filePart,
                    physicianId = physicianIdBody,
                    appointmentId = appointmentIdBody,
                    diseasesId = diseasesIdBody
                )

                if (isUploaded) {
                    val newImage = ImagesEntity(
                        images_path = uri.toString(),
                        created_at = getCurrentTimestamp(),
                        physician_id = physicianId,
                        diseases_id = diseasesId,
                        appointment_id = appointmentId
                    )
                    imagesRepository.insertImages(listOf(newImage))

                    withContext(Dispatchers.IO) {
                        val updatedList = _imagesList.value.toMutableList().apply {
                            add(newImage)
                        }
                        _imagesList.value = updatedList
                    }
                    Log.d("Upload", "Upload thành công và lưu vào Room!")
                } else {
                    Log.e("Upload", "Upload thất bại!")
                }
            } catch (e: Exception) {
                Log.e("Upload", "Lỗi khi upload: ${e.message}", e)
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

    fun getCurrentTimestamp(): String {
        return java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
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
}