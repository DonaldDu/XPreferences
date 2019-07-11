package com.dhy.xpreference

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException

import java.util.ArrayList

class GsonConverter @JvmOverloads constructor(gson: Gson? = null) : ObjectConverter {
    private val gson: Gson = gson ?: Gson()

    override fun objectToString(obj: Any): String {
        return gson.toJson(obj)
    }

    override fun <V> string2object(string: String, dataClass: Class<V>): V {
        return gson.fromJson(string, dataClass)
    }
}
