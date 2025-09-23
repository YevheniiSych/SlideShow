package com.test.slideshow.data.common

import android.content.Context
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class FileDownloader @Inject constructor(
    private val context: Context
) {
    suspend fun downloadFile(url: String, fileName: String): File {
        val file = File(context.filesDir, fileName)

        if (file.exists()) return file

        withContext(Dispatchers.IO) {
            URL(url).openStream().use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
        }
        return file
    }
}