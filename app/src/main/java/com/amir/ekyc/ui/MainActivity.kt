package com.amir.ekyc.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.amir.ekyc.R
import com.lib.ekyc.presentation.ui.document.ExtractDocumentActivity
import com.lib.ekyc.presentation.ui.nfc.GetDataForNFCEncryptionActivity
import com.lib.ekyc.presentation.ui.face.FaceDetectionActivity
import com.lib.ekyc.presentation.utils.base.DetectionType
import com.lib.ekyc.presentation.utils.base.KYC
import com.lib.ekyc.presentation.utils.base.KYC.Companion.IMAGE_URL
import com.lib.ekyc.presentation.utils.base.KYC.Companion.RESULTS
import com.lib.ekyc.presentation.utils.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.extractDocumentContainer).setOnClickListener {

            ExtractDocumentActivity.start(
                activity = this,
                detectionType = DetectionType.DOCUMENT
            )
            ExtractDocumentActivity.start(
                activity = this,
                detectionType = DetectionType.DOCUMENT,
                mandatoryFields = arrayListOf("name , family")
            )

        }

        findViewById<View>(R.id.faceDetectionContainer).setOnClickListener {
            FaceDetectionActivity.start(this)
        }

        findViewById<View>(R.id.nfcContainer).setOnClickListener {
            GetDataForNFCEncryptionActivity.start(
                activity = this
            )
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
        //NFC extraction
        if (requestCode == KYC.SCAN_PASSPORT_NFC_RESULTS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val results = data?.getSerializableExtra(RESULTS)
                "$results".toast(this)
            }
        }
    }
}