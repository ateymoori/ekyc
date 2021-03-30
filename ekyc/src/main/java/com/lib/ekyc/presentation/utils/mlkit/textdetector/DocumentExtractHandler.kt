package com.lib.ekyc.presentation.utils.mlkit.textdetector

import android.graphics.Bitmap
import com.google.mlkit.vision.text.Text
import java.lang.Exception

interface DocumentExtractHandler {

    fun onExtractionFailed(image: Bitmap , msg:String?)
    fun onExtractionSuccess(image: Bitmap, visionText: Text)
}