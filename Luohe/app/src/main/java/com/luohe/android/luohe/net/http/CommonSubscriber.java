package com.luohe.android.luohe.net.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.user.TokenHandler;
import com.luohe.android.luohe.utils.LoadingHelper;
import com.luohe.android.luohe.utils.ToastUtil;

import rx.Subscriber;

/**
 * @author GanQuan
 * @Time: 16/4/4
 */
public abstract class CommonSubscriber<T> extends Subscriber<T> {
	ErrorHandler mErrorHandler;
	private Context mContext;

	public CommonSubscriber(Context context) {
		this.mContext = context;
	}

	public CommonSubscriber<T> onError(ErrorHandler error) {
		this.mErrorHandler = error;
		return this;
	}

	@Override
	public void onNext(T t) {
		if (t == null) {
			onError(new NullPointerException("result is null"));
			return;
		}
		if (t instanceof Result) {
			Result result = (Result) t;
			if (!TokenHandler.onTokenError(result, mContext)) {
				onSuccess(t);
			}
		} else {
			onSuccess(t);
		}
	}

	public abstract void onSuccess(T result);

	@Override
	public void onCompleted() {
		LogUtils.e("gq", "onCompleted");
	}

	@Override
	public void onError(Throwable e) {
		Log.e("okhttp", e.toString());
		if (mErrorHandler != null)
			this.mErrorHandler.onError(e);
	}

	public interface ErrorHandler {
		void onError(Throwable e);

	}

}
