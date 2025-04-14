package com.example.healthy_diagnosis.core.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

fun prepareImagePart(context: Context, uri: Uri): MultipartBody.Part {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri) ?: throw Exception("Cannot open URI")

    val fileBytes = inputStream.readBytes()
    val requestFile = fileBytes.toRequestBody("image/*".toMediaTypeOrNull())

    val fileName = getFileNameFromUri(context, uri) ?: "upload.jpg"
    return MultipartBody.Part.createFormData("file", fileName, requestFile)
}

fun getFileNameFromUri(context: Context, uri: Uri): String? {
    val returnCursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    returnCursor?.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (nameIndex >= 0) {
                return it.getString(nameIndex)
            }
        }
    }
    return null
}