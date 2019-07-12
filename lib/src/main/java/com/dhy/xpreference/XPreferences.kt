package com.dhy.xpreference

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import java.io.File

object XPreferences : IPreferences {
    inline fun <reified T> get(context: Context, isStatic: Boolean = false): T? {
        return get(context, T::class.java.name, T::class.java, isStatic)
    }

    inline fun <reified T> get(context: Context, key: Enum<*>, isStatic: Boolean = false): T? {
        return get(context, key.keyName(), T::class.java, isStatic)
    }

    inline fun <reified T> get(context: Context, key: String, isStatic: Boolean = false): T? {
        return get(context, key, T::class.java, isStatic)
    }

    override fun putString(context: Context, key: String, obj: String?, isStatic: Boolean) {
        getPreferences(context, isStatic).putString(context, key, obj, isStatic)
    }

    override fun getString(context: Context, key: String, isStatic: Boolean): String? {
        return getPreferences(context, isStatic).getString(context, key, isStatic)
    }

    private fun getPreferences(context: Context, isStatic: Boolean): IPreferences {
        val generator = getGenerator(context)
        return if (isStatic) {
            if (context.hasFilePermission()) StaticPreferences(generator)
            else {
                val msg = "no file permissions for static preferences"
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                Log.e("XPreferences", msg)
                InnerPreferences(generator)
            }
        } else InnerPreferences(generator)
    }

    private fun getGenerator(context: Context): IPreferenceFileNameGenerator {
        if (XPreferencesSetting.generator == null) XPreferencesSetting.init(context)
        return XPreferencesSetting.generator
    }
}

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