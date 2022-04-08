package com.slowmac.autobackgroundremover

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.segmentation.Segmentation
import com.google.mlkit.vision.segmentation.Segmenter
import com.google.mlkit.vision.segmentation.selfie.SelfieSegmenterOptions
import kotlinx.coroutines.*
import java.nio.ByteBuffer

object BackgroundRemover {

    private val segment: Segmenter
    private var buffer = ByteBuffer.allocate(0)
    private var width = 0
    private var height = 0


    init {
        val segmentOptions = SelfieSegmenterOptions.Builder()
                .setDetectorMode(SelfieSegmenterOptions.SINGLE_IMAGE_MODE)
                .build()

        segment = Segmentation.getClient(segmentOptions)
    }


    /**
     * Process the image to get buffer and image height and width
     * */
    fun bitmapForProcessing(image: Bitmap, listener: OnBackgroundChangeListener) {
        val input = InputImage.fromBitmap(image, 0)
        segment.process(input)
            .addOnSuccessListener { segmentationMask ->
                buffer = segmentationMask.buffer
                width = segmentationMask.width
                height = segmentationMask.height

                CoroutineScope(Dispatchers.IO).launch {
                    val bitmap = removeBackgroundFromImage(image)
                    withContext(Dispatchers.Main){
                        listener.onSuccess(bitmap)
                    }
                }

            }
            .addOnFailureListener { e ->
                println("Image processing failed: $e")
                listener.onFailed(e)
            }
    }

    /**
     * Remove the background pixels from the image
     * */
    private suspend fun removeBackgroundFromImage(image: Bitmap): Bitmap {
        val bitmap = CoroutineScope(Dispatchers.IO).async {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val bgConfidence = ((1.0 - buffer.float) * 255).toInt()
                    if (bgConfidence >= 100) {
                        image.setPixel(x, y, 0)
                    }
                }
            }
            buffer.rewind()
            return@async image
        }
        return bitmap.await()
    }

}