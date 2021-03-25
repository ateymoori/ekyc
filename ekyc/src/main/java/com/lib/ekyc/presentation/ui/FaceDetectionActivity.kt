package com.lib.ekyc.presentation.ui

import android.graphics.Bitmap
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

//        binding.captureBtn.setOnClickListener {
//            fileName = System.currentTimeMillis()
//            binding.camera.takePictureSnapshot()
//        }
//        binding.refreshImgv.setOnClickListener {
//            refreshView()
//        }

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
        if (cameraSource != null) {
            cameraSource!!.release()
        }
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
    }

    override fun onFaceNotLocated() {
        "onFaceNotLocated".log("ekyc__ 5")
    }

}