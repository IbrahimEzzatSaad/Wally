package com.example.wally.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.IOException

fun Context.getImageUri(bitmap: Bitmap?): Uri? {
    val bytes = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        put(MediaStore.Images.Media.IS_PENDING, 1)

    }

    val resolver = contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    return try {
        uri?.let { uri ->
            val outputStream = resolver.openOutputStream(uri)
            outputStream?.use {
                it.write(bytes.toByteArray())
            }

            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)


            uri
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}