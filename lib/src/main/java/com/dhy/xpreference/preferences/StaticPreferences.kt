package com.dhy.xpreference.preferences

import android.content.Context
import com.dhy.xpreference.IPreferences
import com.dhy.xpreference.staticDirectory
import com.dhy.xpreference.util.IPreferenceFileNameGenerator
import org.apache.commons.io.FileUtils
import java.io.File
import java.nio.charset.Charset

class StaticPreferences(private val generator: IPreferenceFileNameGenerator) :
    IPreferences {
    override fun putString(context: Context, key: Class<*>, obj: String?, isStatic: Boolean) {
        val file = getPrefsFile(context, key)
        if (obj != null) {
            FileUtils.writeStringToFile(file, obj, Charset.defaultCharset())
        } else {
            if (file.exists()) file.delete()
        }
    }

    override fun getString(context: Context, key: Class<*>, isStatic: Boolean): String? {
        val file = getPrefsFile(context, key)
        if (file.exists()) {
            return FileUtils.readFileToString(file, Charset.defaultCharset())
        }
        return null
    }

    private fun getPrefsFile(context: Context, key: Class<*>): File {
        val root = context.staticDirectory
        val preferencesName = generator.generate(context, key)
        val file = File(root, "prefs" + File.separator + preferencesName)
        file.parentFile.apply {
            if (!exists()) mkdirs()
        }
        return file
    }
}