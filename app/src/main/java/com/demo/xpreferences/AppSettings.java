package com.demo.xpreferences;

import android.content.Context;
import android.support.annotation.NonNull;
import com.dhy.xpreference.SingleInstance;
import com.dhy.xpreference.util.StaticPref;

import java.io.Serializable;

@StaticPref
public class AppSettings extends SingleInstance implements Serializable {
    public static AppSettings getInstance(@NonNull Context context) {
        return getInstance(context, AppSettings.class);
    }

    public Long startDate;
}
