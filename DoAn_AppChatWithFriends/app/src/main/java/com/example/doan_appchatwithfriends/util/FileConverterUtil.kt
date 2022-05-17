package com.example.doan_appchatwithfriends.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream
//Trong thư mục util là các chuyển đổi hay thành phần phụ của view
//Đây dùng để chuyển đổi file thành mảng byte
fun convertFileToByteArray(context: Context, uri: Uri): ByteArray {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

    return byteArrayOutputStream.toByteArray()
}
