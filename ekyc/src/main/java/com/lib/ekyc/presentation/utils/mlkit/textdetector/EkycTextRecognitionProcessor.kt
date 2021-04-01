package com.lib.ekyc.presentation.utils.mlkit.textdetector

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import java.util.ArrayList

class EkycTextRecognitionProcessor(
    private val bitmap: Bitmap,
    private val mandatoryFields: ArrayList<String>?,
    private val handler: DocumentExtractHandler?
) {

    init {
        runTextRecognition(bitmap)
    }

    private fun runTextRecognition(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient()

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                processResult(bitmap, visionText)
            }
            .addOnFailureListener {
                handler?.onExtractionFailed(bitmap, it.message)
            }
    }

    private fun processResult(bitmap: Bitmap, visionText: Text) {
        var error: String? = null

        when {
            visionText.textBlocks.isNullOrEmpty() -> {
                error = "Cannot find any string in the image."
            }
            else -> {
                mandatoryFields?.forEach {
                    if (!visionText.text.contains(it, true)) {
                        error = "Cannot find $it in the image."
                    }
                }
            }
        }

        if (!error.isNullOrEmpty()) {
            handler?.onExtractionFailed(bitmap, error)
        } else {
            handler?.onExtractionSuccess(bitmap, visionText)
        }


    }

}
