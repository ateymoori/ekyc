package com.lib.ekyc.presentation.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.google.mlkit.vision.text.Text
import com.lib.ekyc.databinding.ActivityExtractDocumentBinding
import com.lib.ekyc.presentation.utils.*
import com.lib.ekyc.presentation.utils.KYC.Companion.DETECTION_TYPE
import com.lib.ekyc.presentation.utils.KYC.Companion.MANDATORY_LIST
import com.lib.ekyc.presentation.utils.KYC.Companion.RESULTS
import com.lib.ekyc.presentation.utils.KYC.Companion.SCAN_DOCUMENT_REQUEST_CODE
import com.lib.ekyc.presentation.utils.KYC.Companion.SCAN_DOCUMENT_RESULTS_REQUEST_CODE
import com.lib.ekyc.presentation.utils.mlkit.textdetector.DocumentExtractHandler
import com.lib.ekyc.presentation.utils.mlkit.textdetector.EkycTextRecognitionProcessor
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import java.io.File


class ExtractDocumentActivity : BaseActivity(), DocumentExtractHandler {

    private var mandatoryFields: java.util.ArrayList<String>? = null
    private lateinit var binding: ActivityExtractDocumentBinding
    private var fileName: Long? = null
    private lateinit var image: Bitmap
    private lateinit var outPutResult: String
    private lateinit var detectionType: DetectionType

    companion object {
        fun start(
            activity: Activity,
            detectionType: DetectionType,
            mandatoryFields: ArrayList<String>? = null
        ) {
            val starter = Intent(activity, ExtractDocumentActivity::class.java)
            starter.putStringArrayListExtra(MANDATORY_LIST, mandatoryFields)
            starter.putExtra(DETECTION_TYPE, detectionType.ordinal)
            activity.startActivityForResult(starter, SCAN_DOCUMENT_REQUEST_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtractDocumentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mandatoryFields = intent.getStringArrayListExtra(MANDATORY_LIST)
        detectionType = DetectionType.values()[intent.getIntExtra(
            DETECTION_TYPE,
            DetectionType.DOCUMENT.ordinal
        )]

        checkPermission(
            Manifest.permission.CAMERA
        ) { result ->
            when (result) {
                PERMISSION_RESULT.GRANTED -> startCamera()
                else -> showError("Need permission")
            }
        }

        binding.captureBtn.setOnClickListener {
            fileName = System.currentTimeMillis()
            binding.camera.takePictureSnapshot()
        }
        binding.refreshImgv.setOnClickListener {
            refreshView()
            binding.captureBtn.slideUp()
            binding.refreshImgv.slideDown()
            binding.nextBtn.slideDown()
        }

        binding.camera.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                result.toFile(
                    File(getFilePath())
                ) {
                    image = BitmapFactory.decodeFile(getFilePath())
                    binding.preview.setImageBitmap(image)
                    binding.captureBtn.slideDown()
                    binding.refreshImgv.slideUp()
                    binding.nextBtn.slideUp()
                }
            }
        })

        binding.nextBtn.setOnClickListener {
            EkycTextRecognitionProcessor(image, mandatoryFields, this)
        }

    }

    private fun startCamera() {
        binding.camera.setLifecycleOwner(this)
    }

    fun getFilePath(): String {
        val directory = ContextWrapper(applicationContext).getDir("imageDir", Context.MODE_PRIVATE)
        return File(directory, fileName.toString() + ".jpg").absolutePath
    }

    fun refreshView() {
        binding.preview.setImageResource(0)
        binding.captureBtn.slideUp()
        binding.refreshImgv.slideDown()
        binding.nextBtn.slideDown()
    }

    override fun onExtractionFailed(image: Bitmap, msg: String?) {
        msg.toast(this)
    }

    override fun onExtractionSuccess(image: Bitmap, visionText: Text) {
        outPutResult = visionText.text


        when (detectionType) {
            DetectionType.FACE -> TODO()
            DetectionType.DOCUMENT -> {
                val anotherIntent = Intent(this, ExtractDocumentResultActivity::class.java)
                anotherIntent.putExtra(KYC.IMAGE_URL, getFilePath())
                anotherIntent.putExtra(RESULTS, visionText.text)
                anotherIntent.putStringArrayListExtra(MANDATORY_LIST, mandatoryFields)
                startActivityForResult(anotherIntent, SCAN_DOCUMENT_RESULTS_REQUEST_CODE)
            }
            DetectionType.DOCUMENT_NFC -> {
                startActivity(Intent(this, NFCReaderActivity::class.java))
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //document extraction
        if (requestCode == KYC.SCAN_DOCUMENT_RESULTS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val resultIntent = Intent()
                resultIntent.putExtra(KYC.IMAGE_URL, getFilePath())
                resultIntent.putExtra(RESULTS, outPutResult)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                refreshView()
            }
        }
    }


}