package com.lib.ekyc.presentation.ui.nfc

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.lib.ekyc.databinding.ActivityNfcReaderBinding
import com.lib.ekyc.presentation.utils.base.*
import com.lib.ekyc.presentation.utils.base.KYC.Companion.BIRTHDATE
import com.lib.ekyc.presentation.utils.base.KYC.Companion.EXPIRY
import com.lib.ekyc.presentation.utils.base.KYC.Companion.NFC_TIMEOUT
import com.lib.ekyc.presentation.utils.base.KYC.Companion.PASSPORT_NUMBER
import com.lib.ekyc.presentation.utils.gone
import com.lib.ekyc.presentation.utils.nfc.Image
import com.lib.ekyc.presentation.utils.nfc.ImageUtil
import com.lib.ekyc.presentation.utils.toast
import com.lib.ekyc.presentation.utils.visible
import net.sf.scuba.smartcards.CardService
import org.jmrtd.BACKey
import org.jmrtd.BACKeySpec
import org.jmrtd.PassportService
import org.jmrtd.PassportService.DEFAULT_MAX_BLOCKSIZE
import org.jmrtd.PassportService.NORMAL_MAX_TRANCEIVE_LENGTH
import org.jmrtd.lds.CardSecurityFile
import org.jmrtd.lds.PACEInfo
import org.jmrtd.lds.icao.DG1File
import org.jmrtd.lds.icao.DG2File
import org.jmrtd.lds.iso19794.FaceImageInfo
import java.util.ArrayList

class NFCReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNfcReaderBinding

    private var adapter: NfcAdapter? = null

    var passportNumber: String? = null
    var expirationDate: String? = null
    var birthDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNfcReaderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.guidContainer.visible()
        binding.resultContainer.gone()

        passportNumber = intent.getStringExtra(PASSPORT_NUMBER)?.toUpperCase()
        expirationDate = intent.getStringExtra(EXPIRY)
        birthDate = intent.getStringExtra(BIRTHDATE)
    }

    override fun onResume() {
        super.onResume()

        NfcAdapter.getDefaultAdapter(this)?.let { nfcAdapter ->
            // An Intent to start your current Activity. Flag to singleTop
            // to imply that it should only be delivered to the current
            // instance rather than starting a new instance of the Activity.
            val launchIntent = Intent(this, this.javaClass)
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            // Supply this launch intent as the PendingIntent, set to cancel
            // one if it's already in progress. It never should be.
            val pendingIntent = PendingIntent.getActivity(
                this, 0, launchIntent, PendingIntent.FLAG_CANCEL_CURRENT
            )

            // Define your filters and desired technology types
            val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED))
            val techTypes = arrayOf(arrayOf(IsoDep::class.java.name))

            // And enable your Activity to receive NFC events. Note that there
            // is no need to manually disable dispatch in onPause() as the system
            // very strictly performs this for you. You only need to disable
            // dispatch if you don't want to receive tags while resumed.
            nfcAdapter.enableForegroundDispatch(
                this, pendingIntent, filters, techTypes
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            IsoDep.get(tag)?.let { isoDepTag ->
                isoDepTag.timeout = NFC_TIMEOUT
                val bacKey: BACKeySpec = BACKey(passportNumber, birthDate, expirationDate)
                process(IsoDep.get(tag), bacKey)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (adapter != null) {
            adapter!!.disableForegroundDispatch(this)
        }
    }

    private fun process(
        isoDep: IsoDep,
        bacKey: BACKeySpec
    ) {

        try {
            val cardService = CardService.getInstance(isoDep)
            cardService.open()
            val service = PassportService(
                cardService,
                NORMAL_MAX_TRANCEIVE_LENGTH,
                DEFAULT_MAX_BLOCKSIZE,
                true,
                false
            )
            service.open()
            var paceSucceeded = false
            try {
                val cardSecurityFile =
                    CardSecurityFile(service.getInputStream(PassportService.EF_CARD_SECURITY))
                val securityInfoCollection = cardSecurityFile.securityInfos
                for (securityInfo in securityInfoCollection) {
                    if (securityInfo is PACEInfo) {
                        val paceInfo = securityInfo
                        service.doPACE(
                            bacKey,
                            paceInfo.objectIdentifier,
                            PACEInfo.toParameterSpec(paceInfo.parameterId),
                            null
                        )
                        paceSucceeded = true
                    }
                }
            } catch (e: Exception) {
                e.message.toast(this)
            }
            service.sendSelectApplet(paceSucceeded)
            if (!paceSucceeded) {
                try {
                    service.getInputStream(PassportService.EF_COM).read()
                } catch (e: Exception) {
                    service.doBAC(bacKey)
                }
            }

            // -- Personal Details -- //
            val dg1In = service.getInputStream(PassportService.EF_DG1)
            val dg1File = DG1File(dg1In)
            val mrzInfo = dg1File.mrzInfo

            val passportDetail = mrzInfo.toPassportDetail()

            val dg2In = service.getInputStream(PassportService.EF_DG2)
            val dg2File = DG2File(dg2In)
            val faceInfos = dg2File.faceInfos
            val allFaceImageInfos: MutableList<FaceImageInfo> = ArrayList()
            for (faceInfo in faceInfos) {
                allFaceImageInfos.addAll(faceInfo.faceImageInfos)
            }
            if (!allFaceImageInfos.isEmpty()) {
                val faceImageInfo = allFaceImageInfos.iterator().next()
                val image: Image = ImageUtil.getImage(this, faceImageInfo)
               // passportDetail.imageBitmap = image.bitmapImage
                BitmapUtils.bitmapToFile(this, image.bitmapImage) { fileAddress ->
                    passportDetail.imageURI = fileAddress
                }
            }

            showData(passportDetail)

        } catch (e: Exception) {
            e.message.toast(this)
        }

    }

    private fun showData(data: PassportDetailsEntity) {
        binding.detailsList.adapter = DetailsAdapter(data.toList())

        Glide
            .with(this)
            .load(data.imageURI)
            .into(   binding.image)

        binding.guidContainer.gone()
        binding.resultContainer.visible()
        binding.nextBtn.visible()

        binding.nextBtn.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra(KYC.RESULTS, data)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }


}