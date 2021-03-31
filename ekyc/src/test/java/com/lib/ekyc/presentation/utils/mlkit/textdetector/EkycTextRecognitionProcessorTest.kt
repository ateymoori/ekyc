package com.lib.ekyc.presentation.utils.mlkit.textdetector

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.mlkit.vision.text.Text
import com.lib.ekyc.utils.GetBitmap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class EkycTextRecognitionProcessorTest {

    @Mock
    private lateinit var documentExtractHandler: DocumentExtractHandler
    @Mock
    private lateinit var bitmap: Bitmap
    @Mock
    private lateinit var handler: DocumentExtractHandler
    @Mock
    private lateinit var visionText: Text

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Repository onInvoke`() {

        val kyc = mock(EkycTextRecognitionProcessor::class.java )




    }

//    @ExperimentalCoroutinesApi
//    @Test
//    fun `Check results of UseCase`() {
//        runBlockingTest {
//            val items = listOf(SingleValueEntity("Berlin"))
//            val results = Resource.Success<List<SingleValueEntity>>(
//                items
//            )
//            Mockito.`when`(locationRepository.getLocations()).thenReturn(results)
//
//            var actualItems: List<SingleValueEntity>? = listOf<SingleValueEntity>()
//
//            val useCase = GetLocations(locationRepository)
//
//            useCase.invoke()
//                .onSuccess {
//                    actualItems = it
//                }
//
//            Assert.assertEquals(actualItems, items)
//
//        }

}
