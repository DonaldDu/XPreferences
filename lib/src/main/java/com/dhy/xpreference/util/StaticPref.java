package com.dhy.xpreference.util;

import com.dhy.xpreference.SingleInstance;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

/**
 * for  {@link SingleInstance} only
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE, PARAMETER})
public @interface StaticPref {
}
