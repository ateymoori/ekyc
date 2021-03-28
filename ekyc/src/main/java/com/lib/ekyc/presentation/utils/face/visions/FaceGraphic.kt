package com.lib.ekyc.presentation.utils.face.visions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.lib.ekyc.presentation.utils.face.common.GraphicOverlay
import com.lib.ekyc.presentation.utils.face.common.GraphicOverlay.Graphic
import com.lib.ekyc.presentation.utils.face.common.RectModel
import com.lib.ekyc.presentation.utils.face.interfaces.FaceDetectStatus


class FaceGraphic internal constructor(
    overlay: GraphicOverlay,
    private val firebaseVisionFace: FirebaseVisionFace,
    private val facing: Int,
    private val overlayBitmap: Bitmap
) :
    Graphic(overlay) {
//    lateinit var faceDetectStatus: FaceDetectionProcessor

    //    private final Paint facePositionPaint;
    //    private final Paint idPaint;
    private val boxPaint: Paint = Paint()
    lateinit var faceDetectStatus: FaceDetectStatus

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    override fun draw(canvas: Canvas) {
        val face = firebaseVisionFace ?: return
        boxPaint.color = Color.GREEN


        // Draws a circle at the position of the detected face, with the face's track id below.
        // An offset is used on the Y axis in order to draw the circle, face id and happiness level in the top area
        // of the face's bounding box
        val x = translateX(face.boundingBox.centerX().toFloat())
        val y = translateY(face.boundingBox.centerY().toFloat())

        // Draws a bounding box around the face.
        val xOffset = scaleX(face.boundingBox.width() / 2.0f)
        val yOffset = scaleY(face.boundingBox.height() / 2.0f)
        val left = x - xOffset
        val top = y - yOffset
        val right = x + xOffset
        val bottom = y + yOffset
        //        canvas.drawRect(left, top, right, bottom, boxPaint.setColor(Color.RED));
//        if (left < 190 && top < 450 && right > 850 && bottom > 1050) {
        if (left < 190 && top < 450 && right > 850 && bottom > 1050) {

            faceDetectStatus.onFaceLocated(RectModel(left, top, right, bottom))

            //uncomment if you want check eyes status as well
//            when {
//                face.leftEyeOpenProbability < 0.4 -> {
//                    faceDetectStatus.onErrorOnFace("Left eye is close")
//                }
//                face.rightEyeOpenProbability < 0.4 -> {
//                    faceDetectStatus.onErrorOnFace("Right eye is close")
//                }
//                else -> {
//                    faceDetectStatus.onFaceLocated(RectModel(left, top, right, bottom))
//                }
//            }
            boxPaint.color = Color.GREEN
        } else {
            faceDetectStatus.onFaceNotLocated()
            boxPaint.color = Color.RED
        }
        canvas.drawRect(left, top, right, bottom, boxPaint)
    }

    companion object {
        private const val ID_TEXT_SIZE = 30.0f
        private const val BOX_STROKE_WIDTH = 5.0f
    }

    init {
        boxPaint.style = Paint.Style.STROKE
        boxPaint.strokeWidth = BOX_STROKE_WIDTH
    }
}