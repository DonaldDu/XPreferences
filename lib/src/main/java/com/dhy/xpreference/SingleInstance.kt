package com.dhy.xpreference

import android.content.Context
import android.widget.Toast
import com.dhy.xpreference.util.StaticPref
import com.dhy.xpreference.util.StaticPrefDebugOnly
import java.util.*

abstract class SingleInstance {

    fun save(context: Context) {
        save(context, this)
    }

    fun clearBuffer() {
        clearBuffer(javaClass)
    }

    companion object {
        @Transient
        private val it = WeakHashMap<String, Any>()

        inline fun <reified T : XPref> get(context: Context): T {
            return get(context, T::class.java)
        }

        @JvmStatic
        fun save(context: Context, data: Any) {
            XPreferences.put(context, data, isStaticXPref(context, data.javaClass))
        }

        @JvmStatic
        fun <T : XPref> get(context: Context, cls: Class<T>): T {
            var instance = it[cls.name]
            if (instance == null) {
                instance = XPreferences.get(context, cls, isStaticXPref(context, cls))
                it[cls.name] = instance
            }
            return cls.cast(instance)!!
        }

        @JvmStatic
        fun <T> get(cls: Class<T>): T? {
            val instance = it[cls.name]
            return cls.cast(instance)
        }

        @JvmStatic
        fun clearBuffer(cls: Class<*>) {
            it.remove(cls.name)
        }
    }
}

fun isStaticXPref(context: Context, cls: Class<*>): Boolean {
    val isStatic = if (XPreferencesSetting.debug && cls.isAnnotationPresent(StaticPrefDebugOnly::class.java)) true
    else cls.isAnnotationPresent(StaticPref::class.java)
    return if (isStatic) {
        if (context.hasFilePermission()) true
        else {
            val msg = "no file permissions for static preferences"
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            false
        }
    } else false
}