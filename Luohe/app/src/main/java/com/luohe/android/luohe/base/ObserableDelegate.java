package com.luohe.android.luohe.base;

import android.util.Log;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.net.http.OnReceive;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by GanQuan on 16/4/22.
 */
public class ObserableDelegate implements IObserable {
	CompositeSubscription mSubscriptionGroup;// 用于取消异步任务的会调

	@Override
	public void addSubscription(Subscription s) {

		if (mSubscriptionGroup == null) {
			mSubscriptionGroup = new CompositeSubscription();
		}
		mSubscriptionGroup.add(s);

	}

	@Override
	public void cancelAllSubscript() {
		if (mSubscriptionGroup != null)
			mSubscriptionGroup.clear();
	}

	@Override
	public <T> Subscription onHandleNetRequest(Observable<T> observable, final OnReceive<T> commonSubscriber) {
		Subscription s = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<T>() {
					@Override
					public void onCompleted() {
						// do nothing
					}

					@Override
					public void onError(Throwable e) {
						LogUtils.e("onHandleNetRequest", e.toString());
						commonSubscriber.onError(e);
					}

					@Override
					public void onNext(T t) {
						commonSubscriber.onNext(t);
					}
				});
		addSubscription(s);
		return s;
	}

}
