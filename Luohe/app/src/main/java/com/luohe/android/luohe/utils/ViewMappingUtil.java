package com.luohe.android.luohe.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

public class ViewMappingUtil {
	/**
	 * @param object
	 * @description 用于在类上映射layout
	 */
	public static void mapView(Object object, Activity activity) {
		Context context = activity;
		ViewMapping viewMapping = object.getClass().getAnnotation(ViewMapping.class);
		View rootView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				viewMapping.id(), null);
		activity.setContentView(rootView);
		mapView(object, rootView);
	}

	/**
	 * 映射指定class的类中字段
	 * 
	 * @param object
	 * @param rootView
	 * @param clazz
	 */
	public static void mapView(Object object, View rootView, Class<?> clazz) {
		if (!clazz.isInstance(object)) {
			return;
		}
		startReflect(object, rootView, clazz, false);

	}

	public static void mapView(Object object, View rootView) {
		startReflect(object, rootView, object.getClass(), false);
	}

	public static void mapViewSuper(Object object, View rootView) {
		startReflect(object, rootView, object.getClass(), true);
	}

	public static void mapView(Object object, View rootView, int layer) {
		startReflect(object, rootView, object.getClass(), false);
		Class<?> clazz = object.getClass().getSuperclass();
		for (int i = 0; i < layer - 1; i++, clazz = clazz.getSuperclass()) {
			startReflect(object, rootView, clazz, false);
		}
	}

	private static void startReflect(Object object, View rootView, Class<?> clazz, boolean isSuper) {
		Field[] fields = isSuper ? clazz.getFields() : clazz.getDeclaredFields();
		ViewMapping viewMapping;
		for (Field field : fields) {
			viewMapping = field.getAnnotation(ViewMapping.class);
			int id = 0;
			if (viewMapping != null) {
				try {
					id = viewMapping.id();
					field.setAccessible(true);
					field.set(object, rootView.findViewById(id));
				} catch (Exception e) {
					throw new RuntimeException(id + " map error!\n" + "field:" + field.getName());
				}
			}
		}
	}

	public static View mapView(Object object, Context context) {
		return mapView(object, context, null, false);
	}

	public static View mapView(Object object, Context context, ViewGroup parent, boolean isAttach) {
		ViewMapping viewMapping = object.getClass().getAnnotation(ViewMapping.class);
		View rootView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
				viewMapping.id(), parent, isAttach);
		mapView(object, rootView);
		return rootView;
	}

}
