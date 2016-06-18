package com.luohe.android.luohe.common.eventbus;

import org.simple.eventbus.EventBus;

/**
 * Author: GanQuan Date: 16/5/24 Email:ganquan3640@gmail.com
 */

public class EventBusControl {
	public static final String WRITE_THEME_SUC = "WRITE_THEME_SUC";
	public static final String CHOICE_TIME = "CHOICE_TIME";
	public static final String LOG_OUT = "LOG_OUT";
	public static final String SELECT_REPORT_TYPE = "SELECT_REPORT_TYPE";
	public static final String REFRESH_MY_LUOHE="REFRESH_MY_LUOHE";

	public static void register(Object object) {
		EventBus.getDefault().register(object);
	}

	public static void unregister(Object object) {
		EventBus.getDefault().unregister(object);
	}

	public static void post(Object object, String tag) {
		EventBus.getDefault().post(object, tag);
	}

	public static void postSticky(Object object, String tag) {
		EventBus.getDefault().postSticky(object, tag);
	}

	public static void removeSticky(Class cls, String tag) {
		EventBus.getDefault().removeStickyEvent(cls, tag);
	}

	public static void registerSticky(Object object) {
		EventBus.getDefault().registerSticky(object);
	}
}
