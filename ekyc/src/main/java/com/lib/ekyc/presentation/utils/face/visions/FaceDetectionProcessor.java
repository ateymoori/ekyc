// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.lib.ekyc.presentation.utils.face.visions;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.lib.ekyc.R;
import com.lib.ekyc.presentation.utils.face.common.CameraImageGraphic;
import com.lib.ekyc.presentation.utils.face.common.FrameMetadata;
import com.lib.ekyc.presentation.utils.face.common.GraphicOverlay;
import com.lib.ekyc.presentation.utils.face.common.RectModel;
import com.lib.ekyc.presentation.utils.face.common.VisionProcessorBase;
import com.lib.ekyc.presentation.utils.face.interfaces.FaceDetectStatus;
import com.lib.ekyc.presentation.utils.face.interfaces.FrameReturn;

import java.io.IOException;
import java.util.List;


public class FaceDetectionProcessor extends VisionProcessorBase<List<FirebaseVisionFace>> implements FaceDetectStatus {

    private static final String TAG = "FaceDetectionProcessor";
    public FaceDetectStatus faceDetectStatus = null;
    private final FirebaseVisionFaceDetector detector;

    private final Bitmap overlayBitmap;

    public FrameReturn frameHandler = null;

    public FaceDetectionProcessor(Resources resources) {
        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .build();

        detector = FirebaseVision.getInstance().getVisionFaceDetector(options);

        overlayBitmap = BitmapFactory.decodeResource(resources, R.drawable.clown_nose);
    }

    @Override
    public void stop() {
        try {
            detector.close();
        } catch (IOException e) {
            Log.e(TAG, "Exception thrown while trying to close Face Detector: " + e);
        }
    }

    @Override
    protected Task<List<FirebaseVisionFace>> detectInImage(FirebaseVisionImage image) {
        return detector.detectInImage(image);
    }

    @Override
    protected void onSuccess(
            @Nullable Bitmap originalCameraImage,
            @NonNull List<FirebaseVisionFace> faces,
            @NonNull FrameMetadata frameMetadata,
            @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.clear();
        if (originalCameraImage != null) {
            CameraImageGraphic imageGraphic = new CameraImageGraphic(graphicOverlay, originalCameraImage);
            graphicOverlay.add(imageGraphic);
        }
        for (int i = 0; i < faces.size(); ++i) {
            FirebaseVisionFace face = faces.get(i);
            if (frameHandler != null) {
                frameHandler.onFrame(originalCameraImage, face, frameMetadata, graphicOverlay);
            }
            int cameraFacing =
                    frameMetadata != null ? frameMetadata.getCameraFacing() :
                            Camera.CameraInfo.CAMERA_FACING_BACK;
            FaceGraphic faceGraphic = new FaceGraphic(graphicOverlay, face, cameraFacing, overlayBitmap);
            faceGraphic.faceDetectStatus = this;
            graphicOverlay.add(faceGraphic);

//            if (face.getLeftEyeOpenProbability() < 0.4) {
//                onErrorOnFace("Left eye is close");
//            }
//            if (face.getRightEyeOpenProbability() < 0.4) {
//                onErrorOnFace("Right eye is close");
//            }
        }
        if (faces.size() > 1){
            onMultiFaceLocated();
        }
        graphicOverlay.postInvalidate();
    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Face detection failed " + e);
    }

    @Override
    public void onFaceLocated(RectModel rectModel) {
        if (faceDetectStatus != null) faceDetectStatus.onFaceLocated(rectModel);
    }

    @Override
    public void onFaceNotLocated() {
        if (faceDetectStatus != null) faceDetectStatus.onFaceNotLocated();
    }

    @Override
    public void onMultiFaceLocated() {
        if (faceDetectStatus != null) faceDetectStatus.onMultiFaceLocated();
    }

    @Override
    public void onErrorOnFace(String msg) {
        if (faceDetectStatus != null) faceDetectStatus.onErrorOnFace(msg);
    }
}
