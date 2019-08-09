package com.demo.xpreferences

import android.app.Application
import android.content.Context
import com.dhy.xpreference.XPreferencesSetting
import com.dhy.xpreference.util.GsonConverter
import com.dhy.xpreference.util.IPreferenceFileNameGenerator

class App : Application() {
    var userId: Int = 0
    override fun onCreate() {
        super.onCreate()
        XPreferencesSetting.setConverter(this, GsonConverter::class.java)
        XPreferencesSetting.setGenerator(this, MultUserFileNameGenerator::class.java)
    }

    companion object {
        fun getUserId(context: Context): Int {
            val app = context.applicationContext as App
            return app.userId
        }
    }
}

private class MultUserFileNameGenerator : IPreferenceFileNameGenerator {
    override fun generate(context: Context, prefClass: Class<*>): String {
        return if (isMultUserData(prefClass)) {
            val uid = App.getUserId(context)
            "${prefClass.name}-$uid"
        } else prefClass.name
    }
}