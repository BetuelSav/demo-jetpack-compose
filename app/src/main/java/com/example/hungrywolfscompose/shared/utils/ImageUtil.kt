package com.example.hungrywolfscompose.shared.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import java.io.IOException

class ImageUtil(private val context: Context, private val filename: String) {
    fun deletePhotoFromInternalStorage(): Boolean {
        return try {
            context.deleteFile(filename)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun loadPhotoFromInternalStorage(): Bitmap? {
        context.filesDir.listFiles()
            ?.firstOrNull { it.canRead() && it.isFile && it.name.equals("$filename.jpg") }
            ?.let { file ->
                return BitmapFactory.decodeByteArray(file.readBytes(), 0, file.readBytes().size)
            } ?: return null
    }

    fun savePhotoToInternalStorage(bitmap: Bitmap): Boolean {
        return try {
            context.openFileOutput("$filename.jpg", MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            val bitmap = ImageDecoder.decodeBitmap(source)
            bitmap
        } catch (e: Exception) {
            null
        }
    }
}