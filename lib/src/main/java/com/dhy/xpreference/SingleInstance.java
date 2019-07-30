package com.dhy.xpreference;

import android.content.Context;
import android.support.annotation.NonNull;
import com.dhy.xpreference.util.StaticPref;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.WeakHashMap;

public abstract class SingleInstance {
    private static volatile Map<String, Object> it = new WeakHashMap<>();

    @NonNull
    protected static <T> T getInstance(@NonNull Context context, @NonNull Class<T> cls) {
        Object instance = it.get(cls.getName());
        if (instance == null) {
            instance = XPreferences.INSTANCE.get(context, cls, isStatic(cls));
            if (instance == null) {
                try {
                    Constructor<T> constructor = cls.getConstructor();
                    if (!constructor.isAccessible()) constructor.setAccessible(true);
                    instance = constructor.newInstance();
                } catch (Exception e) {
                    throw new IllegalStateException("SingleInstance newInstance error: " + cls.getName(), e);
                }
            }
            it.put(cls.getName(), instance);
        }
        return (T) instance;
    }

    private static boolean isStatic(@NonNull Class cls) {
        return cls.isAnnotationPresent(StaticPref.class);
    }

    public void save(@NonNull Context context) {
        XPreferences.INSTANCE.put(context, this, isStatic(getClass()));
    }

    public void clearBuffer() {
        it.remove(getClass().getName());
    }
}
