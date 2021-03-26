package com.lib.ekyc.presentation.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lib.ekyc.databinding.ActivityExtractDocumentResultBinding


class ExtractDocumentResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExtractDocumentResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtractDocumentResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val results = intent.getStringExtra("result")
        val image = intent.getStringExtra("image")

        binding.document.setImageBitmap(BitmapFactory.decodeFile(image))
        binding.result.text = results


    }
}