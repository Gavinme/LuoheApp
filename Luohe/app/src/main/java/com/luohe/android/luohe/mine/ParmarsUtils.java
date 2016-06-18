package com.luohe.android.luohe.mine;

import com.google.gson.annotations.SerializedName;
import com.luohe.android.luohe.net.data.ApiParams;

import java.lang.reflect.Field;

/**
 * Author: GanQuan Date: 16/5/28 Email:ganquan3640@gmail.com
 */

public class ParmarsUtils {
	public static ApiParams getParamas(Object obj) {
		{
			ApiParams apiParams = new ApiParams();
			Class<?> cls = obj.getClass();
			for (Field field : cls.getFields()) {
				try {
					if (field.get(obj) instanceof String) {
						SerializedName annotation = field.getAnnotation(SerializedName.class);
						if (annotation == null) {
							apiParams.with(field.getName(), field.get(obj).toString());
						} else {

							apiParams.with(annotation.value(), field.get(obj).toString());
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

			}
			return apiParams;
		}
	}
}
