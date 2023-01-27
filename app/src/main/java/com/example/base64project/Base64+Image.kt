package com.example.base64project

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64


fun String.getImageBase64(width: Int = 300, height: Int = 150): Bitmap? {
    val base64 = if (this.contains("data:image/png;base64")) {
        val split = this.split(",")
        if (split.isEmpty()) this
        else split[split.size - 1]
    } else this

    val decode = Base64.decode(base64, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.size)
    return Bitmap.createScaledBitmap(bitmap, width, height, false)
}

fun String.test () {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }

    val base64 = if (this.contains("data:image/png;base64")) {
        val split = this.split(",")
        if (split.isEmpty()) this
        else split[split.size - 1]
    } else this

    val decode = Base64.decode(base64, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.size)

    val imageHeight: Int = options.outHeight
    val imageWidth: Int = options.outWidth
    val imageType: String = options.outMimeType
}

fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2
        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

fun String.decodeSampledBitmapFromResource(): Bitmap {
    return BitmapFactory.Options().run {
        inJustDecodeBounds = true
        val base64 = if (this@decodeSampledBitmapFromResource.contains("data:image/png;base64")) {
            val split = this@decodeSampledBitmapFromResource.split(",")
            if (split.isEmpty()) this@decodeSampledBitmapFromResource
            else split[split.size - 1]
        } else this@decodeSampledBitmapFromResource

        val decode = Base64.decode(base64, Base64.DEFAULT)


        // Calculate inSampleSize
        inSampleSize = calculateInSampleSize(this, 300, 150)

        // Decode bitmap with inSampleSize set
        inJustDecodeBounds = false
        BitmapFactory.decodeByteArray(decode, 0, decode.size)
    }
}


