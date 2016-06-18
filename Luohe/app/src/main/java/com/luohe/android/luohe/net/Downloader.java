package com.luohe.android.luohe.net;

import android.content.Context;

import com.androidplus.net.HttpConnection;
import com.androidplus.net.HttpRequest;
import com.luohe.android.luohe.net.data.ApiParams;
import com.luohe.android.luohe.user.UserInfoUtil;

import java.util.HashMap;
import java.util.Map;

public class Downloader {
	public static final String TAG = "Downloader";
	private Context mContext;
	private HttpConnection mHttpConnection;

	public Downloader(Context context) {
		mContext = context.getApplicationContext();
		mHttpConnection = new HttpConnection();
	}

	/**
	 * 请求网络数据 *
	 */
	public String getJsonContent(String url, Map<String, String> formParams) {
		HttpRequest request = HttpRequest.getRequest(mContext, url, HttpRequest.METHOD_POST, formParams);
		HashMap<String, String> headerHost = new HashMap<String, String>();
		request.setHeaderParams(headerHost);
		request.setConnectTimeOut(60000);
		request.setReadTimeOut(60000);
		if (formParams == null) {
			formParams = new ApiParams();

		}
		formParams.put("token", UserInfoUtil.getInstance().getUserInfo().getToken());
		return mHttpConnection.getContent(request);
	}

}
