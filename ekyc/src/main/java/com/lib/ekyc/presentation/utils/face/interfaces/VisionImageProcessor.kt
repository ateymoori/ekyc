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
package com.lib.ekyc.presentation.utils.face.interfaces;

import android.graphics.Bitmap;

import com.lib.ekyc.presentation.utils.face.common.FrameMetaData
import com.lib.ekyc.presentation.utils.face.common.GraphicOverlay;

import java.nio.ByteBuffer;


/** An interface to process the images with different ML Kit detectors and custom image models. */
interface VisionImageProcessor {

    /** Processes the images with the underlying machine learning models. */
    fun process(data: ByteBuffer, frameMetadata: FrameMetaData, graphicOverlay: GraphicOverlay)


    /** Processes the bitmap images. */
    fun process(bitmap: Bitmap, graphicOverlay: GraphicOverlay);

    /** Stops the underlying machine learning model and release resources. */
    fun stop();
}
