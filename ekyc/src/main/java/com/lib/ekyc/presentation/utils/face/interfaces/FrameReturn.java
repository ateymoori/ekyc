package com.lib.ekyc.presentation.utils.face.interfaces;

import android.graphics.Bitmap;

import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.lib.ekyc.presentation.utils.face.common.FrameMetadata;
import com.lib.ekyc.presentation.utils.face.common.GraphicOverlay;


public interface FrameReturn{
    void onFrame(
            Bitmap image ,
            FirebaseVisionFace face ,
            FrameMetadata frameMetadata,
            GraphicOverlay graphicOverlay
    );
}