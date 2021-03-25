package com.amir.ekyc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lib.ekyc.presentation.ui.ExtractDocumentActivity
import com.lib.ekyc.presentation.ui.FaceDetectionActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.extract).setOnClickListener {
            startActivity(Intent(this, ExtractDocumentActivity::class.java))
        }
        findViewById<View>(R.id.face).setOnClickListener {
            startActivity(Intent(this, FaceDetectionActivity::class.java))
        }
    }
}