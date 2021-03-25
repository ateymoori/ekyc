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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.lib.ekyc.presentation.utils.face.common.GraphicOverlay;
import com.lib.ekyc.presentation.utils.face.common.RectModel;
import com.lib.ekyc.presentation.utils.face.interfaces.FaceDetectStatus;


/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
public class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float ID_TEXT_SIZE = 30.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private int facing;

    private final Paint facePositionPaint;
    private final Paint idPaint;
    private final Paint boxPaint;

    private volatile FirebaseVisionFace firebaseVisionFace;

    private final Bitmap overlayBitmap;

    FaceDetectStatus faceDetectStatus = null;


    FaceGraphic(GraphicOverlay overlay, FirebaseVisionFace face, int facing, Bitmap overlayBitmap) {
        super(overlay);

        firebaseVisionFace = face;
        this.facing = facing;
        this.overlayBitmap = overlayBitmap;
        final int selectedColor = Color.GREEN;

        facePositionPaint = new Paint();
        facePositionPaint.setColor(selectedColor);

        idPaint = new Paint();
        idPaint.setColor(selectedColor);
        idPaint.setTextSize(ID_TEXT_SIZE);

        boxPaint = new Paint();
        boxPaint.setColor(selectedColor);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        FirebaseVisionFace face = firebaseVisionFace;
        if (face == null) {
            return;
        }

        // Draws a circle at the position of the detected face, with the face's track id below.
        // An offset is used on the Y axis in order to draw the circle, face id and happiness level in the top area
        // of the face's bounding box
        float x = translateX(face.getBoundingBox().centerX());
        float y = translateY(face.getBoundingBox().centerY());

        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getBoundingBox().width() / 2.0f);
        float yOffset = scaleY(face.getBoundingBox().height() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left, top, right, bottom, boxPaint);

        if (
                left < 190 &&
                        top < 450 &&
                        right > 850 &&
                        bottom > 1050
        ) {

            if (faceDetectStatus != null)
                faceDetectStatus.onFaceLocated(new RectModel(left, top, right, bottom));
        } else {
            if (faceDetectStatus != null) faceDetectStatus.onFaceNotLocated();
        }

    }

}
