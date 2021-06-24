package com.dhy.xpreference.util

import android.content.Context
import androidx.annotation.Keep
import com.google.gson.Gson
@Keep
interface ObjectConverter {
    /**
     * init once after newInstance
     * */
    fun init(context: Context): ObjectConverter

    fun objectToString(obj: Any): String

    fun <V> string2object(string: String, dataClass: Class<V>): V
}

class GsonConverter : ObjectConverter {
    private lateinit var gson: Gson
    override fun init(context: Context): ObjectConverter {
        gson = Gson()
        return this
    }

    override fun objectToString(obj: Any): String {
        return gson.toJson(obj)
    }

    override fun <V> string2object(string: String, dataClass: Class<V>): V {
        return gson.fromJson(string, dataClass)
    }
}