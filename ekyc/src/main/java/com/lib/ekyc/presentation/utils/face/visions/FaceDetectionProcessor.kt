package com.lib.ekyc.presentation.utils.face.visions

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.lib.ekyc.R
import com.lib.ekyc.presentation.utils.face.common.*
import com.lib.ekyc.presentation.utils.face.interfaces.FaceDetectStatus
import com.lib.ekyc.presentation.utils.face.interfaces.FrameReturn
import java.io.IOException

class FaceDetectionProcessor(resources: Resources?) :
    VisionProcessorBaseKT<List<FirebaseVisionFace?>?>(), FaceDetectStatus {
    var faceDetectStatus: FaceDetectStatus? = null
    private val detector: FirebaseVisionFaceDetector
    private val overlayBitmap: Bitmap
    var frameHandler: FrameReturn? = null
    override fun stop() {
        try {
            detector.close()
        } catch (e: IOException) {
            Log.e(
                TAG,
                "Exception thrown while trying to close Face Detector: $e"
            )
        }
    }

    override fun detectInImage(image: FirebaseVisionImage): Task<List<FirebaseVisionFace?>?> {
        return detector.detectInImage(image)
    }

    override fun onSuccess(
        originalCameraImage: Bitmap?,
        results: List<FirebaseVisionFace?>?,
        frameMetadata: FrameMetaDataKT,
        graphicOverlay: GraphicOverlay
    ) {

        graphicOverlay.clear()
        if (originalCameraImage != null) {
            val imageGraphic = CameraImageGraphicKT(graphicOverlay, originalCameraImage)
            graphicOverlay.add(imageGraphic)
        }
        results?.forEach { face ->
            if (frameHandler != null) {
                frameHandler!!.onFrame(originalCameraImage, face, frameMetadata, graphicOverlay)
            }
            val cameraFacing = frameMetadata.cameraFacing ?: Camera.CameraInfo.CAMERA_FACING_BACK
            // FaceGraphic faceGraphic = new FaceGraphic(graphicOverlay, face, cameraFacing, overlayBitmap);
            val faceGraphic = FaceGraphic(
                graphicOverlay,
                face!!, cameraFacing, overlayBitmap
            )
            faceGraphic.faceDetectStatus = this
            graphicOverlay.add(faceGraphic)

//            if (face.getLeftEyeOpenProbability() < 0.4) {
//                onErrorOnFace("Left eye is close");
//            }
//            if (face.getRightEyeOpenProbability() < 0.4) {
//                onErrorOnFace("Right eye is close");
//            }
        }
        if (!results.isNullOrEmpty() && results.size > 1) {
            onMultiFaceLocated()
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.e(TAG, "Face detection failed $e")
    }

    override fun onFaceLocated(rectModel: RectModelKT?) {
        if (faceDetectStatus != null) faceDetectStatus!!.onFaceLocated(rectModel)
    }

    override fun onFaceNotLocated() {
        if (faceDetectStatus != null) faceDetectStatus!!.onFaceNotLocated()
    }

    override fun onMultiFaceLocated() {
        if (faceDetectStatus != null) faceDetectStatus!!.onMultiFaceLocated()
    }

    override fun onErrorOnFace(msg: String) {
        if (faceDetectStatus != null) faceDetectStatus!!.onErrorOnFace(msg)
    }

    companion object {
        private const val TAG = "FaceDetectionProcessor"
    }

    init {
        val options = FirebaseVisionFaceDetectorOptions.Builder()
            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .build()
        detector = FirebaseVision.getInstance().getVisionFaceDetector(options)
        overlayBitmap = BitmapFactory.decodeResource(resources, R.drawable.clown_nose)
    }


}
