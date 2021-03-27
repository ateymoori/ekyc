package com.lib.ekyc.presentation.utils.face.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.lib.ekyc.presentation.utils.face.common.GraphicOverlay.Graphic

class CameraImageGraphicKT(overlay: GraphicOverlay?, private val bitmap: Bitmap) : Graphic(overlay) {
    override fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, null, Rect(0, 0, canvas.width, canvas.height), null)
    }
}