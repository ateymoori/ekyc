package com.lib.ekyc.presentation.utils.face.interfaces;


import com.lib.ekyc.presentation.utils.face.common.RectModel;

public interface FaceDetectStatus {
    void onFaceLocated(RectModel rectModel);
    void onFaceNotLocated() ;
}
