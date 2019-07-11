package com.dhy.xpreference

interface ObjectConverter {
    fun objectToString(obj: Any): String

    fun <V> string2object(string: String, dataClass: Class<V>): V
}
