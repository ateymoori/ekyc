package com.lib.ekyc.presentation.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.mlkit.vision.face.FaceDetector
import com.lib.ekyc.databinding.ActivityFaceDetectionBinding
import com.lib.ekyc.presentation.utils.face.common.*
import com.lib.ekyc.presentation.utils.face.interfaces.FaceDetectStatus
import com.lib.ekyc.presentation.utils.face.interfaces.FrameReturn
import com.lib.ekyc.presentation.utils.face.visions.FaceDetectionProcessor
import com.lib.ekyc.presentation.utils.log


class FaceDetectionActivity : AppCompatActivity(), FrameReturn, FaceDetectStatus {

    private lateinit var binding: ActivityFaceDetectionBinding
    private var cameraSource: CameraSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceDetectionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cameraSource = CameraSource(this, binding.fireFaceOverlay)

        try {
            val processor = FaceDetectionProcessor(resources)
            processor.frameHandler = this
            processor.faceDetectStatus = this
            cameraSource?.setMachineLearningFrameProcessor(processor)
        } catch (e: Exception) {
            "ekyc__ 1 $e ".log()
        }

    }

    private fun startCamera() {
        if (cameraSource != null) {
            binding.camera.start(cameraSource, binding.fireFaceOverlay)
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
        frameMetadata: FrameMetadata?,
        graphicOverlay: GraphicOverlay?
    ) {
        face.toString().log("ekyc__ 3")
    }

    override fun onFaceLocated(rectModel: RectModel?) {
        rectModel.toString().log("ekyc__ 4")
        showSuccessMessage("Your face detected successfully")

    }

    override fun onFaceNotLocated() {
        "onFaceNotLocated".log("ekyc__ 5")
        showErrorMessage("Face is not in correct area")
    }

    override fun onMultiFaceLocated() {
        "onMultiFaceLocated".log("ekyc__ 3")
        showErrorMessage("More than one face are in the screen")
    }

    override fun onErrorOnFace(msg: String) {
         showErrorMessage(msg)
    }

    fun showSuccessMessage(msg:String){
        binding.message.setTextColor(Color.GREEN)
        binding.message.text = msg
    }

    fun showErrorMessage(msg:String){
        binding.message.setTextColor(Color.RED)
        binding.message.text = msg
    }

}