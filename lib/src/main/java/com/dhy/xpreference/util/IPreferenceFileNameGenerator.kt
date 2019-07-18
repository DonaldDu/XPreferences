package com.dhy.xpreference.util

import android.content.Context

interface IPreferenceFileNameGenerator {
    /**
     * @param keyName class full name, Enum :"${class.name}_$fieldName". <p/>
     * multiple user can be "$keyName-$uid"
     * */
    fun generate(context: Context, keyName: String): String {
        return keyName
    }
}

class PreferenceFileNameGenerator : IPreferenceFileNameGenerator