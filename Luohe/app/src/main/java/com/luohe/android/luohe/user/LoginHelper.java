package com.luohe.android.luohe.user;

import android.content.Context;
import android.content.Intent;

/**
 * Created by GanQuan on 16/4/20.
 */
public class LoginHelper {
	private Context mContext;

	public LoginHelper(Context context) {
		this.mContext = context;
	}

	public void doLogin(ILoginListener loginListener) {
		if (UserInfoUtil.getInstance().IsLogin()) {
			loginListener.onLogin();
		} else {
			mContext.startActivity(new Intent(mContext, LoginActivity.class));
			LoginActivity.setLoginListener(loginListener);
		}

	}

	public interface ILoginListener {
		void onLogin();
	}

}
