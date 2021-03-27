package com.amir.ekyc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lib.ekyc.presentation.ui.ExtractDocumentActivity
import com.lib.ekyc.presentation.ui.FaceDetectionActivity
import com.lib.ekyc.presentation.ui.NFCReaderActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.extractDocumentContainer).setOnClickListener {
            ExtractDocumentActivity.start(this )
        }
        findViewById<View>(R.id.faceDetectionContainer).setOnClickListener {
            startActivity(Intent(this, FaceDetectionActivity::class.java))
        }


        findViewById<View>(R.id.nfc).setOnClickListener {
            startActivity(Intent(this, NFCReaderActivity::class.java))
        }
    }
}