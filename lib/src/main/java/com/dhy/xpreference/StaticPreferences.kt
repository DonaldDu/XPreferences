package com.dhy.xpreference

import android.content.Context
import android.text.TextUtils
import org.apache.commons.io.FileUtils
import org.json.JSONObject
import java.io.File
import java.nio.charset.Charset

class StaticPreferences : IPreferences {
    override fun putString(context: Context, key: String, obj: String?, isStatic: Boolean) {
        val file = getPrefsFile(context, key)
        if (obj != null) {
            val jsonObject = JSONObject()
            jsonObject.put(key, obj)
            FileUtils.writeStringToFile(file, jsonObject.toString(), Charset.defaultCharset())
        } else {
            if (file.exists()) file.delete()
        }
    }

    override fun getString(context: Context, key: String, isStatic: Boolean): String? {
        val file = getPrefsFile(context, key)
        if (file.exists()) {
            val json = FileUtils.readFileToString(file, Charset.defaultCharset())
            if (!TextUtils.isEmpty(json)) {
                val jsonObject = JSONObject(json)
                return jsonObject.opt(key) as String?
            }
        }
        return null
    }

    private fun getPrefsFile(context: Context, key: String): File {
        val root = context.staticDirectory
        val preferencesName = XPreferencesSetting.generator.generate(key)
        val file = File(root, "prefs" + File.separator + preferencesName)
        file.parentFile.apply {
            if (!exists()) mkdirs()
        }
        return file
    }
}