package com.dhy.xpreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dhy.xpreference.util.GsonConverter;
import com.dhy.xpreference.util.IPreferenceFileNameGenerator;
import com.dhy.xpreference.util.ObjectConverter;
import com.dhy.xpreference.util.PreferenceFileNameGenerator;

import java.lang.reflect.Constructor;

public class XPreferencesSetting {
    static ObjectConverter converter;
    static IPreferenceFileNameGenerator generator;
    public static boolean debug = false;

    /**
     * @param converter we'll get instance with class.newInstance()
     */
    public static void setConverter(Context context, Class<? extends ObjectConverter> converter) {
        saveUtilClassName(context, ObjectConverter.class, converter);
        XPreferencesSetting.converter = (ObjectConverter) load(context, converter.getName());
        XPreferencesSetting.converter.init(context);
    }

    /**
     * @param generator we'll get instance with class.newInstance()
     */
    public static void setGenerator(Context context, Class<? extends IPreferenceFileNameGenerator> generator) {
        saveUtilClassName(context, IPreferenceFileNameGenerator.class, generator);
        XPreferencesSetting.generator = (IPreferenceFileNameGenerator) load(context, generator.getName());
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        String fileName = XPreferencesSetting.class.getName();
        return context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
    }

    private static <T> void saveUtilClassName(Context context, Class<T> utilInterface, Class<? extends T> util) {
        getSharedPreferences(context)
                .edit()
                .putString(utilInterface.getName(), util.getName())
                .apply();
    }

    @Nullable
    private static String getUtilClassName(Context context, Class utilInterface) {
        return getSharedPreferences(context)
                .getString(utilInterface.getName(), null);
    }

    static void init(Context context) {
        if (converter == null || generator == null) {
            if (converter == null) {
                String className = getUtilClassName(context, ObjectConverter.class);
                if (className == null) className = GsonConverter.class.getName();
                converter = (ObjectConverter) load(context, className);
                converter.init(context);
            }
            if (generator == null) {
                String className = getUtilClassName(context, IPreferenceFileNameGenerator.class);
                if (className == null) className = PreferenceFileNameGenerator.class.getName();
                generator = (IPreferenceFileNameGenerator) load(context, className);
            }
        }
    }

    private static Object load(Context context, @NonNull String className) {
        try {
            Class<?> cls = context.getClassLoader().loadClass(className);
            Constructor<?> constructor = cls.getConstructor();
            if (!constructor.isAccessible()) constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
