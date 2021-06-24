package com.dhy.xpreference

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.dhy.xpreference.preferences.InnerPreferences
import com.dhy.xpreference.preferences.StaticPreferences
import com.dhy.xpreference.util.IPreferenceFileNameGenerator
import com.dhy.xpreference.util.ObjectConverter
import com.google.gson.reflect.TypeToken


object XPreferences : IPreferences {
    inline fun <reified T : XPref> get(context: Context, isStatic: Boolean = false): T {
        return get(context, T::class.java, isStatic)
    }

    override fun putString(context: Context, key: Class<*>, obj: String?, isStatic: Boolean) {
        getPreferences(context, isStatic).putString(context, key, obj, isStatic)
    }

    override fun getString(context: Context, key: Class<*>, isStatic: Boolean): String? {
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

    inline fun <reified T> get(context: Context, key: Enum<*>): T? {
        val type = object : TypeToken<T>() {}.type
        return get(context, key, T::class.java)
    }

    private fun getGenerator(context: Context): IPreferenceFileNameGenerator {
        if (XPreferencesSetting.generator == null) XPreferencesSetting.init(context)
        return XPreferencesSetting.generator
    }
}

interface IPreferences {
    fun put(context: Context, obj: Any, isStatic: Boolean = false) {
        put(context, obj::class.java, obj, isStatic)
    }

    fun put(context: Context, key: Class<*>, obj: Any?, isStatic: Boolean = false) {
        val json = if (obj != null) {
            getConverter(context).objectToString(obj)
        } else null
        putString(context, key, json, isStatic)
    }

    fun putString(context: Context, key: Class<*>, obj: String?, isStatic: Boolean = false)

    fun <T : XPref> get(context: Context, cls: Class<T>, isStatic: Boolean = false): T {
        val json = getString(context, cls, isStatic)
        return if (json != null) {
            getConverter(context).string2object(json, cls)
        } else createNewInstance(cls)
    }

    private fun getSharedPreferences(context: Context, key: Enum<*>): SharedPreferences {
        return context.getSharedPreferences(key.javaClass.name, Activity.MODE_PRIVATE)
    }

    fun <T> get(context: Context, key: Enum<*>, cls: Class<T>): T? {
        val json = getSharedPreferences(context, key)
            .getString(key.name, null)
        return if (json != null) {
            getConverter(context).string2object(json, cls)
        } else null
    }

    fun put(context: Context, key: Enum<*>, obj: Any?) {
        val json = if (obj != null) {
            getConverter(context).objectToString(obj)
        } else null

        getSharedPreferences(context, key)
            .edit()
            .putString(key.name, json)
            .apply()
    }

    fun getString(context: Context, key: Class<*>, isStatic: Boolean = false): String?

    private fun getConverter(context: Context): ObjectConverter {
        if (XPreferencesSetting.converter == null) XPreferencesSetting.init(context)
        return XPreferencesSetting.converter
    }
}

private fun <T : XPref> createNewInstance(cls: Class<T>): T {
    try {
        val constructor = cls.getConstructor()
        if (!constructor.isAccessible) constructor.isAccessible = true
        return constructor.newInstance()
    } catch (e: Exception) {
        throw IllegalStateException("newInstance error: ${cls.name}", e)
    }
}