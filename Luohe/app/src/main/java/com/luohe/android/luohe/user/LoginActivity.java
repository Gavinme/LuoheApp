package com.luohe.android.luohe.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.utils.ToastUtil;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/4/18.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
	@Bind(R.id.tv_account)
	EditText tvAccount;
	@Bind(R.id.tv_password)
	EditText tvPassword;
	@Bind(R.id.tv_forget_pw)
	TextView tvForgetPw;
	@Bind(R.id.tv_login)
	TextView tvLogin;
	private static LoginHelper.ILoginListener monLoginListener;

	public static void setLoginListener(LoginHelper.ILoginListener listener) {
		monLoginListener = listener;
	}

	public static void removeLoginListener() {
		monLoginListener = null;
	}

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setTitle("登录");
		getTitlebar().addAction(new TitleBar.TextAction("注册") {
			@Override
			public void performAction(View view) {
				startActivity(new Intent(LoginActivity.this, RegistActivity.class));
			}

			@Override
			public int getBackground() {
				return 0;
			}
		});
		getTitlebar().setDefauleBackBtn();
		initActions();

	}

	private void initActions() {
		tvLogin.setOnClickListener(this);
		tvForgetPw.setOnClickListener(this);
	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.activity_login;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_login:
			goLogin();
			break;
		case R.id.tv_forget_pw:
			startActivity(new Intent(this, FindPassWordActivity.class));
			break;
		}

	}

	UserLoginInfo userLoginInfo = null;

	private void goLogin() {
		if (isStrEmpty(tvAccount.getText().toString()) || isStrEmpty(tvPassword.getText().toString())) {
			ToastUtil.showToast("请输入用户名或密码");
			return;
		}

		ApiLoader
				.getApiService()
				.login(tvAccount.getText().toString(), tvPassword.getText().toString())
				.flatMap(new Func1<Result<UserLoginInfo>, Observable<Result<UserCommonInfo>>>() {
					@Override
					public Observable<Result<UserCommonInfo>> call(Result<UserLoginInfo> userLoginInfoResult) {
						LogUtils.e("gq", "call");
						if (userLoginInfoResult.isHasReturnValidCode() && userLoginInfoResult.getResult() != null
								&& userLoginInfoResult.getResult().id != 0) {
							userLoginInfo = userLoginInfoResult.getResult();
							LogUtils.e("gq", "success");
							return ApiLoader.getApiService().userInfo(userLoginInfoResult.getResult().id);
						} else {
							LogUtils.e("gq", "fail");
							throw new IllegalArgumentException("get token fail");
						}

					}
				}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<UserCommonInfo>>(this) {
					@Override
					public void onSuccess(Result<UserCommonInfo> result) {
						if (result.isHasReturnValidCode() && result.getResult() != null) {
							ToastUtil.showToast("登录成功！");
							UserInfoUtil.getInstance().updateLoginInfo(userLoginInfo);// on
																						// success
																						// login
							UserInfoUtil.getInstance().updateUserInfo(result.getResult());
							if (monLoginListener != null) {
								monLoginListener.onLogin();
							}
							monLoginListener = null;
							finish();

						} else {
							ToastUtil.showToast("登录失败，用户名或密码错误！");
						}
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						ToastUtil.showToast("登录失败，用户名或密码错误！");

					}
				});
	}
}
