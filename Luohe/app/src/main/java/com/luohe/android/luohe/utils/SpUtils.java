package com.luohe.android.luohe.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.androidplus.util.StringUtil;
import com.luohe.android.luohe.common.MyApplication;

/**
 * Created by Devuser on 2015/6/4.
 */
public class SpUtils {
	private SharedPreferences.Editor editor;
	private SharedPreferences sharedPreferences;
	private final static String SHARED_PREFERENCE_NAME = "SharedPreferences_app";
	private Context mContext;

	private static SpUtils mInstance;

	private SpUtils() {
		mContext = MyApplication.getContext();
		this.sharedPreferences = mContext.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		this.editor = sharedPreferences.edit();
	}

	public synchronized static SpUtils getSpUtilsInstance() {
		if (mInstance == null) {
			mInstance = new SpUtils();
		}
		return mInstance;
	}

	public String getString(String key) {
		return getString(key, null);
	}

	public String getString(String key, String defaultValue) {
		return this.sharedPreferences.getString(key, defaultValue);
	}

	public void setString(String key, String value) {
		this.editor.putString(key, value);
		this.editor.commit();
	}

	public void setBool(String key, boolean bool) {
		this.editor.putBoolean(key, bool);
		this.editor.commit();
	}

	/**
	 * 默认返回为true
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolTrue(String key) {
		return this.sharedPreferences.getBoolean(key, true);
	}

	public boolean getBoolFalse(String key) {
		return this.sharedPreferences.getBoolean(key, false);
	}

	public int getInt(String key, int defaultValue) {
		return this.sharedPreferences.getInt(key, defaultValue);
	}

	public void setInt(String key, int i) {
		this.editor.putInt(key, i);
		this.editor.commit();
	}

}
