package com.lib.ekyc.presentation.ui.face

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.lib.ekyc.R
import com.lib.ekyc.databinding.ActivityFaceDetectionBinding
import com.lib.ekyc.presentation.utils.*
import com.lib.ekyc.presentation.utils.base.BaseActivity
import com.lib.ekyc.presentation.utils.base.BitmapUtils
import com.lib.ekyc.presentation.utils.base.KYC.Companion.FACE_DETECTION_REQUEST_CODE
import com.lib.ekyc.presentation.utils.base.KYC.Companion.IMAGE_URL
import com.lib.ekyc.presentation.utils.base.PERMISSION_RESULT
import com.lib.ekyc.presentation.utils.face.common.*
import com.lib.ekyc.presentation.utils.face.interfaces.FaceDetectStatus
import com.lib.ekyc.presentation.utils.face.interfaces.FrameReturn
import com.lib.ekyc.presentation.utils.face.visions.FaceDetectionProcessor

class FaceDetectionActivity : BaseActivity(), FrameReturn, FaceDetectStatus {

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

        checkPermission(
            Manifest.permission.CAMERA
        ) { result ->
            when (result) {
                PERMISSION_RESULT.GRANTED -> startProcess()
                else -> {
                    showError(getString(R.string.need_permission))
                    finish()
                }
            }
        }
    }


    private fun startProcess() {
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
                getString(R.string.cannot_detect_face).toast(this)
                return@setOnClickListener
            }
            if (faceImage == null) {
                getString(R.string.face_empty).toast(this)
                return@setOnClickListener
            }
            binding.preview.setImageBitmap(faceImage)
            BitmapUtils.bitmapToFile(this, faceImage) { fileAddress ->
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
        showSuccessMessage(getString(R.string.your_face_detected))
        binding.captureBtn.visible()
        faceIsOK = true

    }

    override fun onFaceNotLocated() {
        showErrorMessage(getString(R.string.face_is_not_in_area))
        faceIsOK = false
    }

    override fun onMultiFaceLocated() {
        showErrorMessage(getString(R.string.more_than_one_face_detected))
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