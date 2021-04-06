package com.lib.ekyc

import android.graphics.Bitmap
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.lib.ekyc.utils.TestUtils
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FaceDetectionTest {

    private lateinit var correctFace: Bitmap
    private lateinit var inCorrectFace: Bitmap
    private lateinit var faceDetectorOptions: FirebaseVisionFaceDetectorOptions
    private lateinit var faceDetector: FirebaseVisionFaceDetector

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getContext()
        FirebaseApp.initializeApp(context)
        correctFace = TestUtils.getBitmapFromTestAssets("correct_face.png")
        inCorrectFace = TestUtils.getBitmapFromTestAssets("empty_card.png")

        faceDetectorOptions =
            FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                .setContourMode(FirebaseVisionFaceDetectorOptions.NO_CONTOURS)
                .enableTracking()
                .build()

        // faceDetector object with specified settings
        faceDetector =
            FirebaseVision.getInstance()
                .getVisionFaceDetector(faceDetectorOptions)

    }

    @Test
    fun `CORRECT_FACE_SHOULD_TRUE`() {
        val syncObject = Object()

        // detect the face in the image
        val task = faceDetector.detectInImage(FirebaseVisionImage.fromBitmap(correctFace))
            .addOnSuccessListener { faces ->

                assert(faces.size > 0)

                synchronized(syncObject) {
                    syncObject.notify()
                }
            }
            .addOnFailureListener {
                assert(false)

                synchronized(syncObject) {
                    syncObject.notify()
                }
            }

        synchronized(syncObject) {
            syncObject.wait()
        }
    }

    @Test
    fun `EMPTY_IMAGE_SHOULD_FAIL`() {
        val syncObject = Object()

        // detect the face in the image
        val task = faceDetector.detectInImage(FirebaseVisionImage.fromBitmap(inCorrectFace))
            .addOnSuccessListener { faces ->

                assert(faces.size == 0)

                synchronized(syncObject) {
                    syncObject.notify()
                }
            }
            .addOnFailureListener {
                assert(false)

                synchronized(syncObject) {
                    syncObject.notify()
                }
            }

        synchronized(syncObject) {
            syncObject.wait()
        }
    }

}