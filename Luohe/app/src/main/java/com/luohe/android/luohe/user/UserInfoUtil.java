package com.luohe.android.luohe.user;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.utils.SpUtils;

/**
 * Created by GanQuan on 16/4/16.
 */
public class UserInfoUtil {
	private AccountUserInfo mUserInfo;
	private static UserInfoUtil mInstance;

	public static UserInfoUtil getInstance() {
		if (mInstance == null) {
			synchronized (UserInfoUtil.class) {
				mInstance = new UserInfoUtil();
			}
		}
		return mInstance;
	}

	public boolean checkUid(int uid) {
		if (IsLogin()) {
			if (mUserInfo.getUid() == uid) {
				return true;
			}
		}
		return false;
	}

	public int getUserId() {
		if (mUserInfo != null)

			return mUserInfo.getUid();
		return -1;
	}

	private UserInfoUtil() {
		mUserInfo = new AccountUserInfo();// init empty userInfo
		String userinfoStr = SpUtils.getSpUtilsInstance().getString(ConstantsUtil.access_token);
		if (TextUtils.isEmpty(userinfoStr)) {
			return;
		}
		// init use login info from sdCard
		UserLoginInfo userLoginInfo = new Gson().fromJson(userinfoStr, new TypeToken<UserLoginInfo>() {
		}.getType());
		mUserInfo.updateLogin(userLoginInfo);

	}

	/**
	 * updateLogin user info ,and save in sdCard
	 * 
	 * @param userInfo
	 */
	public void updateLoginInfo(UserLoginInfo userInfo) {
		mUserInfo.updateLogin(userInfo);
		SpUtils.getSpUtilsInstance().setString(ConstantsUtil.access_token, new Gson().toJson(userInfo));
	}

	public void updateUserInfo(UserCommonInfo info) {
		mUserInfo.setComUserInfo(info);
	}

	public void logout() {
		mUserInfo.logOut();
	}

	public AccountUserInfo getUserInfo() {
		return mUserInfo;
	}

	public boolean IsLogin() {
		return getUserInfo().isLogin();
	}

}
