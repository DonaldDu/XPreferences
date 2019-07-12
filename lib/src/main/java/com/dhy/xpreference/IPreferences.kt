package com.dhy.xpreference

import android.content.Context

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

    fun <T> get(context: Context, key: String, cls: Class<T>, isStatic: Boolean = false): T? {
        val json = getString(context, key, isStatic)
        return if (json != null) {
            getConverter(context).string2object(json, cls)
        } else null
    }

    private fun getConverter(context: Context): ObjectConverter {
        val c = if (context is XPreferencesSetting) context else context.applicationContext
        val setting = c as XPreferencesSetting
        return setting.getPreferencesConverter()
    }

    fun getString(context: Context, key: String, isStatic: Boolean = false): String?
}

fun Enum<*>.keyName(): String {
    return "${javaClass.name}_$name"
}