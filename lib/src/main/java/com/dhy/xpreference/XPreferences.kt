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
import com.dhy.xpreference.util.InnerPreferences
import com.dhy.xpreference.util.StaticPreferences
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

interface IPreferences {
    fun put(context: Context, obj: Any, isStatic: Boolean = false) {
        put(context, obj::class.java.name, obj, isStatic)
    }

    fun put(context: Context, key: Class<*>, obj: Any?, isStatic: Boolean = false) {
        put(context, key.name, obj, isStatic)
    }

    fun put(context: Context, key: Enum<*>, obj: Any?, isStatic: Boolean = false) {
        put(context, key.keyName(), obj, isStatic)
    }

    fun put(context: Context, key: String, obj: Any?, isStatic: Boolean = false) {
        val json = if (obj != null) {
            getConverter(context).objectToString(obj)
        } else null
        putString(context, key, json, isStatic)
    }

    fun putString(context: Context, key: String, obj: String?, isStatic: Boolean = false)

    fun <T> get(context: Context, cls: Class<T>, isStatic: Boolean = false): T? {
        return get(context, cls.name, cls, isStatic)
    }

    fun <T> get(context: Context, key: String, cls: Class<T>, isStatic: Boolean = false): T? {
        val json = getString(context, key, isStatic)
        return if (json != null) {
            getConverter(context).string2object(json, cls)
        } else null
    }

    private fun getConverter(context: Context): ObjectConverter {
        if (PreferencesSetting.converter == null) PreferencesSetting.init(context)
        return PreferencesSetting.converter
    }

    fun getString(context: Context, key: String, isStatic: Boolean = false): String?
}

val FILE_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
    arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
} else {
    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
}

fun Enum<*>.keyName(): String {
    return "${javaClass.name}_$name"
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