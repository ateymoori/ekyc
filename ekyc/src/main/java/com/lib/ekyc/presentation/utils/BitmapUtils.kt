package com.lib.ekyc.presentation.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class BitmapUtils {
    companion object {
        fun getFilePath(context: Context, fileName: String): String {
            val directory = ContextWrapper(context).getDir("imageDir", Context.MODE_PRIVATE)
            return File(directory, "$fileName.jpg").absolutePath
        }

        fun bitmapToFile(context: Context, bitmap: Bitmap?, onFinished: (String) -> Unit) {
            val fileAddress = getFilePath(context, System.currentTimeMillis().toString())
            val os: OutputStream
            os = FileOutputStream(fileAddress)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            onFinished.invoke(fileAddress)
        }
    }
}