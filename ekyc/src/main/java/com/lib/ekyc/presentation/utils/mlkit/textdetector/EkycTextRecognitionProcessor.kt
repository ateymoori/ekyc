package com.lib.ekyc.presentation.utils.mlkit.textdetector

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition

class EkycTextRecognitionProcessor(
    private val bitmap: Bitmap,
    private val handler: DocumentExtractHandler
) {

    init {
        runTextRecognition(bitmap)
    }

    private fun runTextRecognition(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient()

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                processResult(bitmap , visionText)
            }
            .addOnFailureListener {
                handler.onExtractionFailed(bitmap)
            }
    }

    private fun processResult(bitmap: Bitmap, visionText: Text) {
        if (visionText.textBlocks.isNullOrEmpty()) {
            handler.onExtractionFailed(bitmap)
        } else {
            handler.onExtractionSuccess(bitmap, visionText)
        }
    }

}
