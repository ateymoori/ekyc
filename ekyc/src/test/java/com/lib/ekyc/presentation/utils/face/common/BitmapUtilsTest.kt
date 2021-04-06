package com.lib.ekyc.presentation.utils.face.common

import org.junit.Test
import org.mockito.Mockito.mock
import java.nio.ByteBuffer


class BitmapUtilsTest {

    @Test(expected = RuntimeException::class)
    fun `getBitmap_should_return_bitmap`() {
        val byteBuffers = mock(ByteBuffer::class.java)
        val frameData = mock(FrameMetaData::class.java)
        val outPut = BitmapUtils.getBitmap(byteBuffers, frameData)
    }

}