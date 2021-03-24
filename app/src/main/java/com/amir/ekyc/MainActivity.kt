package com.amir.ekyc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.lib.ekyc.presentation.ui.ExtractDocumentActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.extract).setOnClickListener {
            startActivity(Intent(this, ExtractDocumentActivity::class.java))
        }
    }
}