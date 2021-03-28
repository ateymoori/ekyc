package com.lib.ekyc.presentation.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.lib.ekyc.databinding.ActivityFaceDetectionBinding
import com.lib.ekyc.presentation.utils.*
import com.lib.ekyc.presentation.utils.BitmapUtils
import com.lib.ekyc.presentation.utils.KYC.Companion.FACE_DETECTION_REQUEST_CODE
import com.lib.ekyc.presentation.utils.KYC.Companion.IMAGE_URL
import com.lib.ekyc.presentation.utils.face.common.*
import com.lib.ekyc.presentation.utils.face.interfaces.FaceDetectStatus
import com.lib.ekyc.presentation.utils.face.interfaces.FrameReturn
import com.lib.ekyc.presentation.utils.face.visions.FaceDetectionProcessor

class FaceDetectionActivity : AppCompatActivity(), FrameReturn, FaceDetectStatus {

    private lateinit var binding: ActivityFaceDetectionBinding
    private var cameraSource: CameraSource? = null
    private var faceIsOK = false
    var faceImage: Bitmap? = null

    companion object {
        fun start(
            activity: Activity
        ) {
            val starter = Intent(activity, FaceDetectionActivity::class.java)
            activity.startActivityForResult(starter, FACE_DETECTION_REQUEST_CODE)
        }
    }

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
            e.message.toast(this)
        }

        binding.captureBtn.setOnClickListener {
            if (!faceIsOK) {
                "Cannot detect face".toast(this)
                return@setOnClickListener
            }
            if (faceImage == null) {
                "Face is Empty".toast(this)
                return@setOnClickListener
            }
            binding.preview.setImageBitmap(faceImage)
            BitmapUtils.bitmapToFile(this ,faceImage) { fileAddress ->
                val resultIntent = Intent()
                resultIntent.putExtra(IMAGE_URL, fileAddress)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

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
        frameMetadata: FrameMetaData?,
        graphicOverlay: GraphicOverlay?
    ) {
        faceImage = image
    }

    override fun onFaceLocated(rectModel: RectModel?) {
        showSuccessMessage("Your face detected successfully")
        binding.captureBtn.visible()
        faceIsOK = true

    }

    override fun onFaceNotLocated() {
        showErrorMessage("Face is not in correct area")
        faceIsOK = false
    }

    override fun onMultiFaceLocated() {
        showErrorMessage("More than one face are in the screen")
        faceIsOK = false
    }

    override fun onErrorOnFace(msg: String) {
        showErrorMessage(msg)
        faceIsOK = false
    }

    private fun showSuccessMessage(msg: String) {
        binding.message.setTextColor(Color.GREEN)
        binding.message.text = msg
    }

    private fun showErrorMessage(msg: String) {
        binding.message.setTextColor(Color.RED)
        binding.message.text = msg
        binding.captureBtn.invisible()
    }


}