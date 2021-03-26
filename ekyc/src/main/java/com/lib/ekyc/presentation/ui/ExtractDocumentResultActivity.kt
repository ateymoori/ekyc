package com.lib.ekyc.presentation.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lib.ekyc.databinding.ActivityExtractDocumentResultBinding


class ExtractDocumentResultActivity : AppCompatActivity() {

    private var mandatoryFields: java.util.ArrayList<String>? = null
    private lateinit var binding: ActivityExtractDocumentResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtractDocumentResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val results = intent.getStringExtra("result")
        val image = intent.getStringExtra("image")
        mandatoryFields = intent.getStringArrayListExtra("list")

        binding.document.setImageBitmap(BitmapFactory.decodeFile(image))
        binding.result.text = results

        binding.scanAgain.setOnClickListener {
            ExtractDocumentActivity.start(this, mandatoryFields)
            finish()
        }
        binding.confirmResult.setOnClickListener {
            finish()
        }


    }
}