package com.lib.ekyc.presentation.utils.face.interfaces;


import com.lib.ekyc.presentation.utils.face.common.RectModelKT;

interface FaceDetectStatus {
    fun onFaceLocated(rectModel: RectModelKT?)

    fun onFaceNotLocated()

    fun onMultiFaceLocated()

    fun onErrorOnFace(msg: String)


}
