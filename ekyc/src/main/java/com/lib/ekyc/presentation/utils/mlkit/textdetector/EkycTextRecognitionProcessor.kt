package com.lib.ekyc.presentation.utils.mlkit.textdetector

import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.lib.ekyc.presentation.utils.mlkit.models.EKycResultData

class EkycTextRecognitionProcessor(
    private val bitmap: Bitmap
) {

    init {
        runTextRecognition(bitmap)
    }

    interface TextExtractionHandler {
        fun onMyKadExtracted(result: EKycResultData)
        fun onExtractionFailed(msg: String?)
    }

    fun build() {
        runTextRecognition(bitmap)
    }


    private fun runTextRecognition(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient()

        val result = recognizer.process(image)
            .addOnSuccessListener { visionText ->
                Log.d("ekyc_" , visionText.text)
            }
            .addOnFailureListener { e ->
                Log.d("ekyc_" , e.toString() )
            }

    }

}
