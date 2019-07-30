package com.dhy.xpreference

import android.content.Context
import com.dhy.xpreference.util.StaticPref
import java.util.*

abstract class SingleInstance {

    fun save(context: Context) {
        XPreferences.put(context, this, isStatic(javaClass))
    }

    fun clearBuffer() {
        it.remove(javaClass.name)
    }

    companion object {
        @Transient
        private val it = WeakHashMap<String, Any>()

        inline fun <reified T> get(context: Context): T {
            return get(context, T::class.java)
        }

        @JvmStatic
        fun <T> get(context: Context, cls: Class<T>): T {
            var instance = it[cls.name]
            if (instance == null) {
                instance = XPreferences.get(context, cls, isStatic(cls))
                if (instance == null) {
                    try {
                        val constructor = cls.getConstructor()
                        if (!constructor.isAccessible) constructor.isAccessible = true
                        instance = constructor.newInstance()
                    } catch (e: Exception) {
                        throw IllegalStateException("SingleInstance newInstance error: " + cls.name, e)
                    }

                }
                it[cls.name] = instance!!
            }
            return cls.cast(instance)!!
        }

        private fun isStatic(cls: Class<*>): Boolean {
            return cls.isAnnotationPresent(StaticPref::class.java)
        }
    }
}
