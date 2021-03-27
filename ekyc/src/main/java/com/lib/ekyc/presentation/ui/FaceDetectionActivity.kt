package com.lib.ekyc.presentation.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.lib.ekyc.databinding.ActivityFaceDetectionBinding
import com.lib.ekyc.presentation.utils.face.common.*
import com.lib.ekyc.presentation.utils.face.interfaces.FaceDetectStatus
import com.lib.ekyc.presentation.utils.face.interfaces.FrameReturn
import com.lib.ekyc.presentation.utils.face.visions.FaceDetectionProcessor
import com.lib.ekyc.presentation.utils.log
import com.lib.ekyc.presentation.utils.slideUp


class FaceDetectionActivity : AppCompatActivity(),FrameReturn, FaceDetectStatus {

    private lateinit var binding: ActivityFaceDetectionBinding
    private var cameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceDetectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cameraSource = CameraSource(this, binding.faceOverlay)

        try {
            val processor = FaceDetectionProcessor(resources)
            processor.frameHandler = this
            processor.faceDetectStatus = this
            cameraSource?.setMachineLearningFrameProcessor(processor)
        } catch (e: Exception) {

        }

    }

    private fun startCamera() {
        if (cameraSource != null) {
            binding.camera.start(cameraSource, binding.faceOverlay)
        }
    }


    override fun onResume() {
        super.onResume()
        startCamera()
    }

    override fun onPause() {
        super.onPause()
        binding.camera.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource?.release()
    }


    override fun onFrame(
        image: Bitmap?,
        face: FirebaseVisionFace?,
        frameMetadata: FrameMetaDataKT?,
        graphicOverlay: GraphicOverlay?
    ) {
        face.toString().log("ekyc__ 3")
    }

    override fun onFaceLocated(rectModel: RectModelKT?) {
        showSuccessMessage("Your face detected successfully")
        binding.captureBtn.slideUp()

    }


    override fun onFaceNotLocated() {
        showErrorMessage("Face is not in correct area")
    }

    override fun onMultiFaceLocated() {
        showErrorMessage("More than one face are in the screen")
    }

    override fun onErrorOnFace(msg: String) {
        showErrorMessage(msg)
    }

    private fun showSuccessMessage(msg: String) {
        binding.message.setTextColor(Color.GREEN)
        binding.message.text = msg
    }

    private fun showErrorMessage(msg: String) {
        binding.message.setTextColor(Color.RED)
        binding.message.text = msg
    }

}