/*
 * Created by Storm Zhang, Feb 11, 2014.
 */

package com.luohe.android.luohe.net.data;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.luohe.android.luohe.user.UserInfoUtil;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonRequest<T> extends Request<T> {
	private final Gson mGson = new Gson();
	private final TypeToken<T> mTypeToken;
	private final Listener<T> mListener;
	private final Map<String, String> mHeaders;
	private final Map<String, String> mParams;

	public GsonRequest(String url, TypeToken<T> typeToken, Map<String, String> params, Listener<T> listener,
			ErrorListener errorListener) {
		this(Method.POST, url, typeToken, null, params, listener, errorListener);
		if (params == null) {
			params = new ApiParams();
			addCommonParams(params);
		} else {
			addCommonParams(params);
		}

	}

	private void addCommonParams(Map<String, String> params) {
		params.put("token", UserInfoUtil.getInstance().getUserInfo().getToken());
	}

	/**
	 * @param method
	 * @param url
	 * @param typeToken
	 * @param headers
	 * @param params
	 * @param listener
	 * @param errorListener
	 */
	private GsonRequest(int method, String url, TypeToken<T> typeToken, Map<String, String> headers,
			Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.mTypeToken = typeToken;
		this.mHeaders = headers;
		this.mParams = params;
		this.mListener = listener;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mParams != null ? mParams : super.getParams();
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return mHeaders != null ? mHeaders : super.getHeaders();
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			T result = getResult(json, mTypeToken);
			return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	public T getResult(String jsonData, TypeToken<T> type) {
		return (T) mGson.fromJson(jsonData, type.getType());
	}
}
