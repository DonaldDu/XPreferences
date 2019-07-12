package com.demo.xpreferences

import android.content.Context
import com.dhy.xpreference.IPreferenceFileNameGenerator

class MultUserFileNameGenerator : IPreferenceFileNameGenerator {
    override fun generate(context: Context, keyName: String): String {
        val uid = App.getUserId(context)
        return "$keyName-$uid"
    }
}