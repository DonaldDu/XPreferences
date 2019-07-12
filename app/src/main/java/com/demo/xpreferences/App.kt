package com.demo.xpreferences

import android.app.Application
import android.content.Context
import com.dhy.xpreference.XPreferencesSetting

class App : Application(), XPreferencesSetting {
    override fun getPreferencesGenerator() = MultUserFileNameGenerator()
    var userId: Int = 0

    companion object {
        fun getUserId(context: Context): Int {
            val app = context.applicationContext as App
            return app.userId
        }
    }
}