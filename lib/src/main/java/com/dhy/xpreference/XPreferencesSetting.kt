package com.dhy.xpreference

import android.content.Context
import com.dhy.xpreference.util.GsonConverter
import com.dhy.xpreference.util.PreferenceFileNameGenerator

/**
 * 通过接口实现配置。如果用Static变量的话，在内存低时可能会被回收，然后就会出错。
 * */
interface XPreferencesSetting {
    fun getPreferencesConverter(): ObjectConverter = GsonConverter()
    fun getPreferencesGenerator(): IPreferenceFileNameGenerator = PreferenceFileNameGenerator()
}

interface ObjectConverter {
    fun objectToString(obj: Any): String

    fun <V> string2object(string: String, dataClass: Class<V>): V
}

interface IPreferenceFileNameGenerator {
    /**
     * @param keyName 通常为类全名，如果是Enum则为：Enum类名 + 下划线 + 变量名，如：  "${javaClass.name}_$name"。<p>
     * 如果要支持多用户，可以设计为："$keyName-$uid"
     * */
    fun generate(context: Context, keyName: String): String {
        return keyName
    }
}