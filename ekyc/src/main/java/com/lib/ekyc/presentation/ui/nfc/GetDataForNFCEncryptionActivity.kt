package com.lib.ekyc.presentation.ui.nfc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lib.ekyc.databinding.ActivityGetDataForNfcEncryptionBinding
import com.lib.ekyc.presentation.utils.base.AppUtil
import com.lib.ekyc.presentation.utils.base.DocumentValidationUtil
import com.lib.ekyc.presentation.utils.base.KYC
import com.lib.ekyc.presentation.utils.base.KYC.Companion.RESULTS
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

class GetDataForNFCEncryptionActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    lateinit var calendar: Calendar
    lateinit var datePicker: DatePickerDialog
    private lateinit var binding: ActivityGetDataForNfcEncryptionBinding

    var passportNumber = "N46409799"
    var expirationDate = "230902"
    var birthDate = "890703"

    companion object {
        fun start(
            activity: Activity
        ) {
            val starter = Intent(activity, GetDataForNFCEncryptionActivity::class.java)
            activity.startActivityForResult(starter, KYC.SCAN_PASSPORT_NFC_RESULTS_REQUEST_CODE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetDataForNfcEncryptionBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        calendar = Calendar.getInstance()
        datePicker = DatePickerDialog.newInstance(
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        binding.passportNumber.setText(passportNumber)
        binding.birthday.setText(birthDate)
        binding.expiry.setText(expirationDate)

        binding.birthday.setOnClickListener {
            showDatePicker(DATETYPE.BIRTHDATE.name)
        }

        binding.expiry.setOnClickListener {
            showDatePicker(DATETYPE.EXPIRY.name)
        }

        binding.nextBtn.setOnClickListener {
            validateAndProcess(
                pan = binding.passportNumber.text.toString(),
                expiry = binding.expiry.text.toString(),
                birthDate = binding.birthday.text.toString()
            )
        }
    }

    private fun validateAndProcess(pan: String?, expiry: String?, birthDate: String?) {
        if (!DocumentValidationUtil.passportNumberIsValid(pan)) {
            binding.passportNumber.error = "Passport number is invalid"
            return
        }

        if (expiry.isNullOrEmpty()) {
            binding.expiry.error = "This fields is mandatory"
            return
        }
        if (birthDate.isNullOrEmpty()) {
            binding.birthday.error = "This fields is mandatory"
            return
        }

        val anotherIntent = Intent(this, NFCReaderActivity::class.java)
        anotherIntent.putExtra(KYC.PASSPORT_NUMBER, pan)
        anotherIntent.putExtra(KYC.EXPIRY, expiry)
        anotherIntent.putExtra(KYC.BIRTHDATE, birthDate)
        startActivityForResult(anotherIntent, KYC.SCAN_PASSPORT_NFC_RESULTS_REQUEST_CODE)
    }

    private fun showDatePicker(tag: String) {
        datePicker.show(supportFragmentManager, tag)
        datePicker.vibrate(false)
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = AppUtil.convertDate(year, monthOfYear + 1, dayOfMonth)
        when (view?.tag) {
            DATETYPE.BIRTHDATE.name -> {
                binding.birthday.setText(date)
            }
            DATETYPE.EXPIRY.name -> {
                binding.expiry.setText(date)
            }
        }
    }

    enum class DATETYPE {
        BIRTHDATE, EXPIRY
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //document extraction
        if (requestCode == KYC.SCAN_PASSPORT_NFC_RESULTS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val resultIntent = Intent()
                resultIntent.putExtra(KYC.RESULTS, data?.getSerializableExtra(RESULTS))
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}