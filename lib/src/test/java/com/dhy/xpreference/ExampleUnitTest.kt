package com.dhy.xpreference

import org.junit.Assert
import org.junit.Test
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2.toLong())
    }

    @Test
    fun test() {
        val datas: List<String>? = getTestClass()
    }

    private inline fun <reified T> getTestClass(): T? {
        val type = object : TypeReference<T>() {}.type
        if (type is ParameterizedType) {
            type.actualTypeArguments.forEach { println(it.typeName) }
        }
        return null
    }

    abstract class TypeReference<T> : Comparable<TypeReference<T>> {
        val type: Type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        override fun compareTo(other: TypeReference<T>) = 0
    }
}