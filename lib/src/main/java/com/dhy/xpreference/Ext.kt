package com.dhy.xpreference

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

val FILE_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
} else {
    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
}

fun Context.hasFilePermission(): Boolean {
    return FILE_PERMISSIONS.find {
        ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
    } == null
}

fun Activity.requestFilePermission(requestCode: Int = 1) {
    if (hasFilePermission()) return
    ActivityCompat.requestPermissions(this, FILE_PERMISSIONS, requestCode)
}

val Context.staticDirectory: File
    get() {
        val sdcard = Environment.getExternalStorageDirectory()
        return File(sdcard, "Android/static/$packageName")
    }