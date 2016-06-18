package com.luohe.android.luohe.luohe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.OnReceive;
import com.luohe.android.luohe.user.LoginHelper;
import com.luohe.android.luohe.utils.ToastUtil;

import butterknife.Bind;

/**
 * Created by GanQuan on 16/3/13.
 */
public class WriteThemeActivity extends AppCompatActivity {
	@Bind(R.id.edit_title)
	EditText editTitle;
	@Bind(R.id.tv_count)
	EditText tvCount;
	@Bind(R.id.tv_anony_name)
	CheckBox tvAnonyName;
	@Bind(R.id.bottom_layout)
	LinearLayout bottomLayout;
	@Bind(R.id.edit_des)
	EditText edit_des;
	LoginHelper mLoginHelper = new LoginHelper(this);

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setTitle("写主题");
		getTitlebar().setLeftImageResource(R.drawable.back_btn);
		getTitlebar().addAction(new TitleBar.TextAction("发布") {
			@Override
			public void performAction(View view) {
				mLoginHelper.doLogin(new LoginHelper.ILoginListener() {
					@Override
					public void onLogin() {
						sendTheme();
					}
				});

			}

		});
	}

	private void sendTheme() {
		final ProgressDialog progressDialog = ProgressDialog.show(this, "正在加载...", "");
		onHandleNetRequest(
				ApiLoader.getApiService().publishSubject(editTitle.getText().toString(), getCoinValue(),
						edit_des.getText().toString(), tvAnonyName.isChecked() ? 1 : 0,
						getIntent().getIntExtra(ConstantsUtil.id, 0)), new OnReceive<Result<Void>>() {
					@Override
					public void onError(Throwable e) {
						progressDialog.dismiss();
						ToastUtil.showToast(getString(R.string.send_fail));
					}

					@Override
					public void onNext(Result<Void> voidResult) {
						progressDialog.dismiss();
						if (voidResult != null && voidResult.isHasReturnValidCode()) {
							ToastUtil.showToast(getString(R.string.send_success));
							EventBusControl.post(new Object(), EventBusControl.WRITE_THEME_SUC);
							finish();

						} else {
							ToastUtil.showToast(getString(R.string.send_fail));
						}
					}
				});
	}

	public Integer getCoinValue() {
		int i = 0;
		try {
			i = Integer.valueOf(tvCount.getText().toString());
		} catch (Exception e) {

		}
		return i;
	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.write_theme_activity;
	}

	public static Intent getStartIntent(Context context, int fallOrderId) {
		Intent intent = new Intent(context, WriteThemeActivity.class);
		intent.putExtra(ConstantsUtil.id, fallOrderId);
		return intent;
	}

}
