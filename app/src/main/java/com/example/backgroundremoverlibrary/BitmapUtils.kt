package com.example.backgroundremoverlibrary

import android.content.ContentResolver
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import kotlin.math.roundToInt

/**
 * Merges two bitmaps by drawing them on a canvas
 *
 * Source: https://stackoverflow.com/a/40546729
 */
fun mergeBitmaps(bmp1: Bitmap, bmp2: Bitmap): Bitmap {
    val merged = Bitmap.createBitmap(bmp1.width, bmp1.height, bmp1.config)
    val canvas = Canvas(merged)
    canvas.drawBitmap(bmp1, Matrix(), null)
    canvas.drawBitmap(bmp2, Matrix(), null)
    return merged
}

/**
 * Resizes a bitmap to the given height and width
 */
fun resizeBitmap(bmp: Bitmap, width: Int, height: Int): Bitmap {
    return Bitmap.createScaledBitmap(bmp, width, height, false)
}

/**
 * Resizes a bitmap to the given width and calculates height with respect to the aspect ratio
 *
 * Source: https://stackoverflow.com/a/28921075
 */
fun resizeBitmapWithAspect(bmp: Bitmap, width: Int): Bitmap {
    val aspectRatio: Float = bmp.width / bmp.height.toFloat()
    val height = (width / aspectRatio).roundToInt()
    return resizeBitmap(bmp, width, height)
}

/**
 * Loads a bitmap from an URI
 */
fun getBitmapFromUri(contentResolver: ContentResolver, imageUri: Uri, width: Int): Bitmap {
    val bmp = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
    return resizeBitmapWithAspect(bmp, width)
}

/**
 * Loads a bitmap from resources
 */
fun getBitmapFromRes(res: Resources, id: Int, width: Int): Bitmap {
    val bmp = BitmapFactory.decodeResource(res, id)
    return resizeBitmapWithAspect(bmp, width)
}
