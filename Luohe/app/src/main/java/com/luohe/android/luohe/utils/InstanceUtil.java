package com.luohe.android.luohe.utils;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class InstanceUtil {

    public static <T> T getInstance(Class<T> clazz) {
        return getInstance(clazz, null, null);
    }

    public static <T> T getInstance(Class<T> clazz, Class<?>[] parameterTypes, Object[] arguments) {
        T instance = null;
        Constructor<T> c;
        try {
            if (null == parameterTypes) {
                c = clazz.getDeclaredConstructor((Class[]) null);
                c.setAccessible(true);
                instance = c.newInstance();
            } else {
                c = clazz.getDeclaredConstructor(parameterTypes);
                c.setAccessible(true);
                instance = c.newInstance(arguments);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (null == instance) {
            throw new RuntimeException("can't instantiate " + clazz + "whether it is not static");
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String fullName) {
        Class<T> clazz = null;
        try {
            clazz = (Class<T>) Class.forName(fullName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("class not found " + clazz);
        }
        return getInstance(clazz);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
