package com.ibrahimezzat404.wally.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.widget.Toast
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

fun isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()


fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    try {
        if (isOnMainThread()) {
            doToast(this, msg, length)
        } else {
            Handler(Looper.getMainLooper()).post {
                doToast(this, msg, length)
            }
        }
    } catch (e: Exception) {
    }
}

private fun doToast(context: Context, message: String, length: Int) {
    if (context is Activity) {
        if (!context.isFinishing && !context.isDestroyed) {
            Toast.makeText(context, message, length).show()
        }
    } else {
        Toast.makeText(context, message, length).show()
    }
}
