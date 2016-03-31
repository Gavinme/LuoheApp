package com.luohe.android.luohe.base;

import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by GanQuan on 16/3/26.
 */
public class BindLayoutMapping {
    public static int getLayoutId(Class<?> clazz) {
        BindLayout m = clazz.getAnnotation(BindLayout.class);
        if (m != null) {
            return m.id();
        }
        return 0;

    }
}
