package com.dhy.xpreference;

public class XPreferencesSetting {
    static ObjectConverter converter = new GsonConverter();
    static IPreferenceFileNameGenerator generator = new PreferenceFileNameGenerator();
}
