package com.dhy.xpreference.util

import android.content.Context
import com.dhy.xpreference.MultUserData

interface IPreferenceFileNameGenerator {
    /**
     * multiple user can be "prefClass.name-$uid"
     * @return fileName or absolutePath, absolutePath only for StaticPreferences
     * */
    fun generate(context: Context, prefClass: Class<*>): String {
        return prefClass.name
    }

    fun isMultUserData(prefClass: Class<*>): Boolean {
        return MultUserData::class.java.isAssignableFrom(prefClass)
    }
}

class PreferenceFileNameGenerator : IPreferenceFileNameGenerator