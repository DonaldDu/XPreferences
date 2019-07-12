package com.dhy.xpreference;

import android.content.Context;

class PreferencesSetting {
    static ObjectConverter converter;
    static IPreferenceFileNameGenerator generator;

    static void init(Context context) {
        if (converter == null || generator == null) {
            XPreferencesSetting ps;
            if (context instanceof XPreferencesSetting) {
                ps = (XPreferencesSetting) context;
            } else if (context.getApplicationContext() instanceof XPreferencesSetting) {
                ps = (XPreferencesSetting) context.getApplicationContext();
            } else {
                throw new IllegalStateException("context or application must implements XPreferencesSetting");
            }
            converter = ps.getPreferencesConverter();
            generator = ps.getPreferencesGenerator();
        }
    }
}
