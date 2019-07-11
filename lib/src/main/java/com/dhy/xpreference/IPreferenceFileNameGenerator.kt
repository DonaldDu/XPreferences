package com.dhy.xpreference

interface IPreferenceFileNameGenerator {
    fun generate(keyName: String): String {
        return keyName
    }
}