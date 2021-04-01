package com.lib.ekyc.presentation.utils.mlkit.textdetector

import android.graphics.Bitmap
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.mlkit.vision.text.Text
import com.lib.ekyc.utils.TestUtils
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EKycTextRecognitionProcessorTest {

    @Test
    fun extract_Correct_card_details_by_MLKIT_processor() {
        val syncObject = Object()

        val card = TestUtils.getBitmapFromTestAssets("correct_card.jpg")
        TestCase.assertNotNull(card)


        EkycTextRecognitionProcessor(card, null, object : DocumentExtractHandler {
            override fun onExtractionFailed(image: Bitmap, msg: String?) {
                assert(false)
                synchronized(syncObject) {
                    syncObject.notify()
                }
            }

            override fun onExtractionSuccess(image: Bitmap, visionText: Text) {
                //assert(visionText.text.contains("name", true))

                //check input bitmap equals to output bitmap
                TestCase.assertEquals(card, image)

                synchronized(syncObject) {
                    syncObject.notify()
                }
            }
        })

        synchronized(syncObject) {
            syncObject.wait()
        }

    }


    //this image has not any string
    //Processor should detects empty image
    @Test
    fun detect_EMPTY_card_details_by_MLKIT_processor() {
        val syncObject = Object()

        val card = TestUtils.getBitmapFromTestAssets("empty_card.png")
        TestCase.assertNotNull(card)

        EkycTextRecognitionProcessor(card, null, object : DocumentExtractHandler {
            override fun onExtractionFailed(image: Bitmap, msg: String?) {
                TestCase.assertEquals(card, image)
                assert(true)

                synchronized(syncObject) {
                    syncObject.notify()
                }
            }

            override fun onExtractionSuccess(image: Bitmap, visionText: Text) {
                assert(false)

                synchronized(syncObject) {
                    syncObject.notify()
                }
            }
        })


        synchronized(syncObject) {
            syncObject.wait()
        }
    }


    //this card has name in the details
    //we are checking, if ML kit can detect *name* in the card
    @Test
    fun extract_CARD_with_MANDATORY_FIELDS() {
        val syncObject = Object()

        val card = TestUtils.getBitmapFromTestAssets("correct_card.jpg")
        TestCase.assertNotNull(card)

        EkycTextRecognitionProcessor(card, null, object : DocumentExtractHandler {
            override fun onExtractionFailed(image: Bitmap, msg: String?) {
                assert(false)
                synchronized(syncObject) {
                    syncObject.notify()
                }
            }

            override fun onExtractionSuccess(image: Bitmap, visionText: Text) {
                assert(visionText.text.contains("name", true))

                synchronized(syncObject) {
                    syncObject.notify()
                }
            }
        })

        synchronized(syncObject) {
            syncObject.wait()
        }

    }

}