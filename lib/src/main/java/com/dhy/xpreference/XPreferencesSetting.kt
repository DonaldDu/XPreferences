package com.dhy.xpreference

/**
 * 通过接口实现配置。如果用Static变量的话，在内存低时可能会被回收，然后就会出错。
 * */
interface XPreferencesSetting {
    fun getPreferencesConverter(): ObjectConverter = GsonConverter()
    fun getPreferencesGenerator(): IPreferenceFileNameGenerator = PreferenceFileNameGenerator()
}