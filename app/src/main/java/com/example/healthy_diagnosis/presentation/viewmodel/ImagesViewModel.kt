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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


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

    init {
        fetchImages()
    }
    fun fetchImages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _imagesList.value = imagesRepository.getAllImages()
                Log.d("ImagesViewModel", "Fetched ${_imagesList.value.size} images from repository")
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("ImagesViewModel", "Error fetching images: ${e.message}")
                _isLoading.value = false
            }
        }
    }

    fun uploadImage(
        uri: Uri,
        physicianId: Int,
        appointmentId: Int,
        diseasesId: Int?,
        onResult: (Boolean) -> Unit
    ) {
        val context = application.applicationContext
        viewModelScope.launch {
            try {

                val inputStream = context.contentResolver.openInputStream(uri)
                if (inputStream == null) {
                    Log.e("Upload", "Không thể mở InputStream từ URI.")
                    onResult(false)
                    return@launch
                }

                val filePart = prepareImagePart(context, uri)
                val physicianIdBody = physicianId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val appointmentIdBody = appointmentId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val diseasesIdBody = (diseasesId ?: 0).toString().toRequestBody("text/plain".toMediaTypeOrNull())

                // Gọi uploadImage từ repository
                val response = imagesRepository.uploadImage(
                    image = filePart,
                    physicianId = physicianIdBody,
                    appointmentId = appointmentIdBody,
                    diseasesId = diseasesIdBody
                )

                if (response.isSuccessful) {
                    val imageResponse = response.body()
                    if (imageResponse != null) {
                        Log.d("Upload", "Phản hồi từ server: $imageResponse")
                        val diseaseIdFromServer = imageResponse.diseases_id
                        val images_path = imageResponse.images_path ?: "Chưa lưu được giá trị"
                        val newImage = ImagesEntity(
                            images_path = images_path,
                            created_at = getCurrentTimestamp(),
                            physician_id = physicianId,
                            diseases_id = diseaseIdFromServer,
                            appointment_id = appointmentId
                        )
                        imagesRepository.insertImages(listOf(newImage))
                        withContext(Dispatchers.IO) {
                            val updatedList = _imagesList.value.toMutableList().apply {
                                add(newImage)
                            }
                            _imagesList.value = updatedList

                            fetchImages()
                        }

                        Log.d("Upload", "Upload thành công và đã lưu Room")
                        onResult(true)
                    } else {
                        Log.e("Upload", "Phản hồi từ server không hợp lệ")
                        onResult(false)
                    }
                } else {
                    Log.e("Upload", "Upload lỗi: ${response.errorBody()?.string()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("Upload", "Lỗi khi upload: ${e.message}", e)
                onResult(false)
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

}