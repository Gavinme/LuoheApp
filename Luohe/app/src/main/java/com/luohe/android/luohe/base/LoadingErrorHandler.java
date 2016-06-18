package com.luohe.android.luohe.base;

import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.utils.LoadingHelper;

/**
 * Created by GanQuan on 16/4/16.
 */
public class LoadingErrorHandler implements CommonSubscriber.ErrorHandler {
	LoadingHelper mLoadingHelper;

	public LoadingErrorHandler(LoadingHelper loadingHelper) {
		this.mLoadingHelper = loadingHelper;
	}


	public LoadingErrorHandler setErrorListener(CommonSubscriber.ErrorHandler errorListener) {
		this.mErrorListener = errorListener;
		return this;
	}

	private CommonSubscriber.ErrorHandler mErrorListener;

	@Override
	public void onError(Throwable e) {
		if (mLoadingHelper != null)
			mLoadingHelper.showNetworkError();
		if (mErrorListener != null)
			mErrorListener.onError(e);
	}
}
