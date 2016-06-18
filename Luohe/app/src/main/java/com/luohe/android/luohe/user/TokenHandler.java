package com.luohe.android.luohe.user;

import android.content.Context;
import android.content.Intent;

import com.luohe.android.luohe.net.data.Result;

/**
 * Created by GanQuan on 16/4/24.处理token失效的情况
 */
public class TokenHandler {
	public static boolean onTokenError(Result result, Context context) {
		if (result.getCode() == Result.ERROR_TOKEN) {// 未登录或登录失效时
			UserInfoUtil.getInstance().logout();// clear login msg
			context.startActivity(new Intent(context, LoginActivity.class));
			return true;
		}
		return false;
	}
}
