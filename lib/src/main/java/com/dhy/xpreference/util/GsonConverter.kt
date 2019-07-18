package com.dhy.xpreference.util

import com.dhy.xpreference.ObjectConverter
import com.google.gson.Gson

class GsonConverter @JvmOverloads constructor(gson: Gson? = null) : ObjectConverter {
    private val gson: Gson = gson ?: Gson()

    override fun objectToString(obj: Any): String {
        return gson.toJson(obj)
    }

    override fun <V> string2object(string: String, dataClass: Class<V>): V {
        return gson.fromJson(string, dataClass)
    }
}
