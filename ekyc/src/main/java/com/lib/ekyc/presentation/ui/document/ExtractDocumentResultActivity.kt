package com.lib.ekyc.presentation.ui.document

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lib.ekyc.databinding.ActivityExtractDocumentResultBinding
import com.lib.ekyc.presentation.utils.base.KYC
import com.lib.ekyc.presentation.utils.base.KYC.Companion.RESULTS

class ExtractDocumentResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExtractDocumentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExtractDocumentResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val results = intent.getStringExtra(RESULTS)
        val image = intent.getStringExtra(KYC.IMAGE_URL)

        binding.document.setImageBitmap(BitmapFactory.decodeFile(image))
        binding.result.text = results

        binding.scanAgain.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_CANCELED, resultIntent)
            finish()
        }
        binding.confirmResult.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}