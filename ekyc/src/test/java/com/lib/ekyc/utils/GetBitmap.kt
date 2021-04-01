package com.lib.ekyc.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.lib.ekyc.presentation.utils.log
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.InputStreamReader

class GetBitmap (path: String) {

    lateinit var bitmap: Bitmap

//    init {
//        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
//        reader.toString().log()
//        val imageStream: InputStream = this.javaClass.getResourceAsStream(path)
//        val bufferedInputStream = BufferedInputStream(imageStream);
//        bitmap = BitmapFactory.decodeStream(reader.buffered().);
//    }
}