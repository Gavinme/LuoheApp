package com.luohe.android.luohe.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Author: GanQuan Date: 16/6/12 Email:ganquan3640@gmail.com
 */

public class JsonHelper {

	@SuppressWarnings("unchecked")
	public static <T> T getResult(String jsonData, TypeToken<T> type) {
		Gson gson = new GsonBuilder().create();
		return (T) gson.fromJson(jsonData, type.getType());
	}
}
