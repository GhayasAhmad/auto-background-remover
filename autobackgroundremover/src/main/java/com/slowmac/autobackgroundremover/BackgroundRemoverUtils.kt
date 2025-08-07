package com.slowmac.autobackgroundremover

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.Log
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.segmentation.subject.SubjectSegmentation
import com.google.mlkit.vision.segmentation.subject.SubjectSegmentationResult
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenter
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenterOptions
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import androidx.core.graphics.get
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class BackgroundRemoverUtils(
    private val context: Context,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
) {

    companion object Companion {
        private const val TAG = "MLKitBackgroundRemover"
        private const val CONFIDENCE_THRESHOLD = 0.5f
        private const val MAX_IMAGE_DIMENSION = 1024
        private const val MODEL_DOWNLOAD_TIMEOUT_SECONDS = 30
    }

    private val moduleInstallClient: ModuleInstallClient by lazy {
        ModuleInstall.getClient(context)
    }

    private val segmenter: SubjectSegmenter by lazy {
        SubjectSegmentation.getClient(
            SubjectSegmenterOptions.Builder()
                .enableForegroundBitmap()
                .enableForegroundConfidenceMask()
                .build()
        )
    }

    @Volatile
    private var isModelReady = false
    @Volatile
    private var isModelDownloading = false

    suspend fun ensureModelReady(): Boolean = withContext(Dispatchers.IO) {
        if (isModelReady) return@withContext true
        if (isModelDownloading) return@withContext false

        isModelDownloading = true
        val result = downloadModel()
        isModelDownloading = false
        isModelReady = result
        result
    }

    private suspend fun downloadModel(): Boolean = suspendCancellableCoroutine { cont ->
        val request = ModuleInstallRequest.newBuilder().addApi(segmenter).build()
        moduleInstallClient.installModules(request)
            .addOnSuccessListener { response ->
                if (response.areModulesAlreadyInstalled()) {
                    cont.resume(true)
                } else {
                    waitForModel(cont)
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "Model install failed", it)
                cont.resume(false)
            }
    }

    private fun waitForModel(cont: CancellableContinuation<Boolean>) {
        coroutineScope.launch {
            repeat(MODEL_DOWNLOAD_TIMEOUT_SECONDS) {
                if (checkModelAvailable()) {
                    cont.resume(true)
                    return@launch
                }
                delay(1000)
            }
            Log.e(TAG, "Model download timeout.")
            cont.resume(false)
        }
    }

    private suspend fun checkModelAvailable(): Boolean = suspendCoroutine { cont ->
        val dummyBitmap = createBitmap(100, 100)
        val inputImage = InputImage.fromBitmap(dummyBitmap, 0)
        segmenter.process(inputImage)
            .addOnSuccessListener {
                dummyBitmap.recycle()
                cont.resume(true)
            }
            .addOnFailureListener {
                dummyBitmap.recycle()
                cont.resume(false)
            }
    }

    suspend fun removeBackground(
        bitmap: Bitmap,
        trimEmptyPart: Boolean = false
    ): Bitmap = withContext(Dispatchers.IO) {
        if (!isModelReady) throw IllegalStateException("Model is not ready")

        val input = scaleBitmapIfNeeded(bitmap)
        val inputImage = InputImage.fromBitmap(input, 0)
        val result = segmenter.process(inputImage).await()
        val masked = applyMask(input, result)

        val output = if (input !== bitmap) {
            val scaled = masked.scale(bitmap.width, bitmap.height)
            masked.recycle()
            input.recycle()
            scaled
        } else masked

        // 🔪 Optionally trim transparent parts
        if (trimEmptyPart) trim(output) else output
    }

    private fun scaleBitmapIfNeeded(bitmap: Bitmap): Bitmap {
        val max = maxOf(bitmap.width, bitmap.height)
        return if (max > MAX_IMAGE_DIMENSION) {
            val scale = MAX_IMAGE_DIMENSION.toFloat() / max
            bitmap.scale((bitmap.width * scale).toInt(), (bitmap.height * scale).toInt())
        } else bitmap
    }

    private fun applyMask(bitmap: Bitmap, result: SubjectSegmentationResult): Bitmap {
        val w = bitmap.width
        val h = bitmap.height
        val output = createBitmap(w, h)
        val canvas = Canvas(output)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        result.foregroundConfidenceMask?.let { mask ->
            mask.rewind()
            val pixels = IntArray(w * h)
            for (i in pixels.indices) {
                val confidence = mask.get()
                pixels[i] = if (confidence > CONFIDENCE_THRESHOLD) {
                    bitmap[i % w, i / w]
                } else {
                    Color.TRANSPARENT
                }
            }
            val maskedBitmap = Bitmap.createBitmap(pixels, w, h, Bitmap.Config.ARGB_8888)
            canvas.drawBitmap(maskedBitmap, 0f, 0f, Paint(Paint.ANTI_ALIAS_FLAG))
            maskedBitmap.recycle()
        } ?: result.foregroundBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, Paint(Paint.ANTI_ALIAS_FLAG))
        }

        return output
    }

    fun cleanup() {
        try {
            segmenter.close()
        } catch (e: Exception) {
            Log.e(TAG, "Segmenter cleanup error", e)
        }
    }

    private suspend fun trim(
        bitmap: Bitmap
    ): Bitmap {
        val result = CoroutineScope(Dispatchers.Default).async {
            var firstX = 0
            var firstY = 0
            var lastX = bitmap.width
            var lastY = bitmap.height
            val pixels = IntArray(bitmap.width * bitmap.height)
            bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
            loop@ for (x in 0 until bitmap.width) {
                for (y in 0 until bitmap.height) {
                    if (pixels[x + y * bitmap.width] != Color.TRANSPARENT) {
                        firstX = x
                        break@loop
                    }
                }
            }
            loop@ for (y in 0 until bitmap.height) {
                for (x in firstX until bitmap.width) {
                    if (pixels[x + y * bitmap.width] != Color.TRANSPARENT) {
                        firstY = y
                        break@loop
                    }
                }
            }
            loop@ for (x in bitmap.width - 1 downTo firstX) {
                for (y in bitmap.height - 1 downTo firstY) {
                    if (pixels[x + y * bitmap.width] != Color.TRANSPARENT) {
                        lastX = x
                        break@loop
                    }
                }
            }
            loop@ for (y in bitmap.height - 1 downTo firstY) {
                for (x in bitmap.width - 1 downTo firstX) {
                    if (pixels[x + y * bitmap.width] != Color.TRANSPARENT) {
                        lastY = y
                        break@loop
                    }
                }
            }
            return@async Bitmap.createBitmap(bitmap, firstX, firstY, lastX - firstX, lastY - firstY)
        }
        return result.await()
    }
}

// Coroutine extension to await a Task<T>
suspend fun <T> com.google.android.gms.tasks.Task<T>.await(): T =
    suspendCancellableCoroutine { cont ->
        addOnSuccessListener { cont.resume(it) }
        addOnFailureListener { cont.resumeWithException(it) }
    }

// Bitmap Extension function (non-leaking)
suspend fun Bitmap.removeBackground(
    context: Context,
    trimEmptyPart: Boolean = false,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
): Bitmap {
    val remover = BackgroundRemoverUtils(context, coroutineScope)
    if (!remover.ensureModelReady()) {
        throw IllegalStateException("ML Kit model not available")
    }
    return remover.removeBackground(
        bitmap = this,
        trimEmptyPart = trimEmptyPart
        )
}