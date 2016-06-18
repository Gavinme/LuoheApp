package com.luohe.android.luohe.base;

import com.luohe.android.luohe.net.http.OnReceive;

import rx.Observable;
import rx.Subscription;

/**
 * Created by GanQuan on 16/4/22.
 */
public interface IObserable {
	void addSubscription(Subscription s);

	void cancelAllSubscript();

	<T> Subscription onHandleNetRequest(Observable<T> observable, OnReceive<T> onReceive);
}
