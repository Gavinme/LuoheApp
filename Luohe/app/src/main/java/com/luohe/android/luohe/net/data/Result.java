package com.luohe.android.luohe.net.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GanQuan on 16/3/26.
 */
public class Result<T> implements Serializable {
	// 客户端使用返回结果
	public static final int OK = 0;
	public static final int ERROR_NET = 1;
	public static final int ERROR_NO_RESULT = 1003;
	public static final int ERROR_NO_PERMISSION = 3001;
	// public static final int ERROR_DATA_EMPTY = 5;//返回数据为空
	public static final int ERROR_NEED_LOGIN = 3000;
	public static final int ERROR_NOT_BIND = 2015;
	public static final int ERROR_TOKEN = 1002;
	public static final int ERROR_NO_MORE = 3002;

	@Expose
	@SerializedName("code")
	private int code;
	@Expose
	@SerializedName("msg")
	private String msg;
	@Expose
	@SerializedName("result")
	private T result;

	private int errorCode = OK;

	public Result() {

	}

	public Result(T result) {
		this.result = result;
	}

	public Result(int code, String msg, T result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public int getErrorCode() {
		if (code != 0) {
			errorCode = getErrorCodeFromApiError(code);
		}
		return errorCode;
	}

	private int getErrorCodeFromApiError(int apiErrorCode) {
		switch (apiErrorCode) {
		case ERROR_NEED_LOGIN:
			return ERROR_NEED_LOGIN;
		default:
			return ERROR_NO_RESULT;
		}
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isHasReturnValidCode() {
		return this.getErrorCode() == Result.OK;
	}

	@Override
	public String toString() {
		return "Result{" + "code=" + code + ", msg='" + msg + '\'' + ", result=" + result + '}';
	}

}
