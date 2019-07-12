package com.dhy.xpreference

import android.content.Context

interface IPreferenceFileNameGenerator {
    /**
     * @param keyName 通常为类全名，如果是Enum则为：Enum类名 + 下划线 + 变量名，如：  "${javaClass.name}_$name"。<p>
     * 如果要支持多用户，可以设计为："$keyName-$uid"
     * */
    fun generate(context: Context, keyName: String): String {
        return keyName
    }
}