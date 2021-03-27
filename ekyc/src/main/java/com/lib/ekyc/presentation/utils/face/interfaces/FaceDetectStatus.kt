package com.lib.ekyc.presentation.utils.face.interfaces;


import com.lib.ekyc.presentation.utils.face.common.RectModel;

interface FaceDetectStatus {
    fun onFaceLocated(rectModel: RectModel?)

    fun onFaceNotLocated()

    fun onMultiFaceLocated()

    fun onErrorOnFace(msg: String)


}
