package com.dhy.xpreference

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class InnerPreferences(private val generator: IPreferenceFileNameGenerator) : IPreferences {
    override fun putString(context: Context, key: String, obj: String?, isStatic: Boolean) {
        if (obj != null) {
            getSharedPreferences(context, key).edit().apply {
                putString(key, obj)
                apply()
            }
        } else {
            getSharedPreferences(context, key).edit().clear().apply()
        }
    }

    override fun getString(context: Context, key: String, isStatic: Boolean): String? {
        return getSharedPreferences(context, key).getString(key, null)
    }

    private fun getSharedPreferences(context: Context, key: String): SharedPreferences {
        val preferencesName = generator.generate(context, key)
        return context.getSharedPreferences(preferencesName, Activity.MODE_PRIVATE)
    }
}