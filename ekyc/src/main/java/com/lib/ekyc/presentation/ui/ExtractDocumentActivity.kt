package com.lib.ekyc.presentation.ui

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.google.mlkit.vision.text.Text
import com.lib.ekyc.databinding.ActivityExtractDocumentBinding
import com.lib.ekyc.presentation.utils.*
import com.lib.ekyc.presentation.utils.mlkit.textdetector.DocumentExtractHandler
import com.lib.ekyc.presentation.utils.mlkit.textdetector.EkycTextRecognitionProcessor
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import java.io.File


class ExtractDocumentActivity : BaseActivity(), DocumentExtractHandler {

    private lateinit var binding: ActivityExtractDocumentBinding
    private var fileName: Long? = null
    private lateinit var image: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtractDocumentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
            EkycTextRecognitionProcessor(image, this)
        }

    }

    private fun startCamera() {
        binding.camera.setLifecycleOwner(this)
    }

    fun getFilePath(): String {
        val directory = ContextWrapper(applicationContext).getDir("imageDir", Context.MODE_PRIVATE)
        return File(directory, fileName.toString() + ".jpg").absolutePath
    }

    fun refreshView(){
        binding.preview.setImageResource(0)
    }

    override fun onExtractionFailed(image: Bitmap) {
    }

    override fun onExtractionSuccess(image: Bitmap, visionText: Text) {
        val anotherIntent = Intent(this, ExtractDocumentResultActivity::class.java)
        anotherIntent.putExtra("image", getFilePath())
        anotherIntent.putExtra("result", visionText.text)
        startActivity(anotherIntent)
        finish()


     }




}