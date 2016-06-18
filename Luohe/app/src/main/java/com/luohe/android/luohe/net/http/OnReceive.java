package com.luohe.android.luohe.net.http;

/**
 * Created by GanQuan on 16/4/22.
 */
public interface OnReceive<T> {
	void onError(Throwable e);

	void onNext(T t);
}
