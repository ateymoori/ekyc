package com.amir.ekyc.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.amir.ekyc.R
import com.lib.ekyc.presentation.ui.ExtractDocumentActivity
import com.lib.ekyc.presentation.ui.FaceDetectionActivity
import com.lib.ekyc.presentation.ui.NFCReaderActivity
import com.lib.ekyc.presentation.utils.DetectionType
import com.lib.ekyc.presentation.utils.KYC
import com.lib.ekyc.presentation.utils.KYC.Companion.FACE_DETECTION_REQUEST_CODE
import com.lib.ekyc.presentation.utils.KYC.Companion.IMAGE_URL
import com.lib.ekyc.presentation.utils.KYC.Companion.RESULTS
import com.lib.ekyc.presentation.utils.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.extractDocumentContainer).setOnClickListener {

//            ExtractDocumentActivity.start(
//                activity = this,
//                detectionType = DetectionType.DOCUMENT
//            )
//            ExtractDocumentActivity.start(
//                activity = this,
//                detectionType = DetectionType.DOCUMENT,
//                mandatoryFields = arrayListOf("name , family")
//            )
            ExtractDocumentActivity.start(
                activity = this,
                detectionType = DetectionType.DOCUMENT_NFC
            )
        }

        findViewById<View>(R.id.faceDetectionContainer).setOnClickListener {
            FaceDetectionActivity.start(this)
        }

        findViewById<View>(R.id.nfc).setOnClickListener {
            startActivity(Intent(this, NFCReaderActivity::class.java))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //face detection
        if (requestCode == KYC.FACE_DETECTION_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //face detected
                val fileAddress = data?.getStringExtra(IMAGE_URL)
                fileAddress.toast(this)
            }
        }

        //document extraction
        if (requestCode == KYC.SCAN_DOCUMENT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val fileAddress = data?.getStringExtra(IMAGE_URL)
                val results = data?.getStringExtra(RESULTS)
                "$fileAddress $results".toast(this)
            }
        }


    }
}