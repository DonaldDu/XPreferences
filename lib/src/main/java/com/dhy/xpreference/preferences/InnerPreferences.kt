package com.dhy.xpreference.preferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.dhy.xpreference.IPreferences
import com.dhy.xpreference.util.IPreferenceFileNameGenerator

class InnerPreferences(private val generator: IPreferenceFileNameGenerator) : IPreferences {
    override fun putString(context: Context, key: Class<*>, obj: String?, isStatic: Boolean) {
        val pref = getSharedPreferences(context, key)
        if (obj != null) {
            pref.edit().apply {
                putString(key.name, obj)
                apply()
            }
        } else {
            pref.edit().clear().apply()
        }
    }

    override fun getString(context: Context, key: Class<*>, isStatic: Boolean): String? {
        return getSharedPreferences(context, key).getString(key.name, null)
    }

    private fun getSharedPreferences(context: Context, key: Class<*>): SharedPreferences {
        val preferencesName = generator.generate(context, key)
        return context.getSharedPreferences(preferencesName, Activity.MODE_PRIVATE)
    }
}