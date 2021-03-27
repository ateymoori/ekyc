package com.lib.ekyc.presentation.utils.face.interfaces

import android.graphics.Bitmap
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.lib.ekyc.presentation.utils.face.common.FrameMetaDataKT
import com.lib.ekyc.presentation.utils.face.common.GraphicOverlay


interface FrameReturn {
    fun onFrame(
        image: Bitmap?,
        face: FirebaseVisionFace?,
        frameMetadata: FrameMetaDataKT?,
        graphicOverlay: GraphicOverlay?
    )
}