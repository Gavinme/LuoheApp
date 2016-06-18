package com.luohe.android.luohe.common;

/**
 * Created by GanQuan on 16/3/5.
 */
public class ConstantsUtil {
	public static final boolean DEBUG = true;
	public static String access_token = "access_token";// the key of userInfo
	public static final String id = "id";
	public static final String name = "name";
	public static String app_msg = "---------app_debug_msg----------";
	public static String key="key";
	public static String serach="search";
	public static String bean="bean";

	public interface RequestCode {
		int GO_TO_CAMERA = 64;
		int GO_TO_ALBUM = 128;
	}

	public interface UrlConstant {
		String HOST_URL = "http://api.luohe520.com/";
		//String HOST_URL = "http://192.168.0.113:8080/luohe/";
	}

}
