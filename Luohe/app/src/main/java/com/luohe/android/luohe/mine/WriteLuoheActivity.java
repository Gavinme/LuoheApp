package com.luohe.android.luohe.mine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.LuoheBean;
import com.luohe.android.luohe.net.model.LuoheTimeBean;
import com.luohe.android.luohe.user.TokenHandler;
import com.luohe.android.luohe.utils.ToastUtil;

import org.simple.eventbus.Subscriber;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/4/16.
 */
public class WriteLuoheActivity extends AppCompatActivity implements View.OnClickListener {
	@Bind(R.id.edit_title)
	EditText editTitle;
	@Bind(R.id.edit_des)
	EditText edit_des;
	@Bind(R.id.bottom_layout)
	RadioGroup bottomLayout;
	@Bind(R.id.tv_select_time)
	TextView tv_select_time;
	@Bind(R.id.cb_public)
	RadioButton cb_public;

	LuoheTimeBean mLuoheTime;
	int mIsPublic;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EventBusControl.register(this);
	}

	@Override
	protected void onDestroy() {
		EventBusControl.unregister(this);
		super.onDestroy();

	}

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().addAction(new TitleBar.TextAction(getString(R.string.send)) {
			@Override
			public void performAction(View view) {
				sendLuohe();

			}

		});
		tv_select_time.setOnClickListener(this);
		cb_public.setChecked(true);
		bottomLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.cb_public:
					mIsPublic = 0;
					break;
				case R.id.cb_anony_name:
					mIsPublic = 1;
					break;
				}
			}
		});

	}

	private void sendLuohe() {
		if (mLuoheTime == null) {
			ToastUtil.showToast("请选择时长");
			return;
		}
		if (isStrEmpty(editTitle.getText().toString())) {
			ToastUtil.showToast("标题不能为空");
			return;
		}
		if (isStrEmpty(edit_des.getText().toString())) {
			ToastUtil.showToast("内容不能为空");
			return;
		}
		final ProgressDialog progressDialog = ProgressDialog.show(this, getString(R.string.is_loading), "");
		ApiLoader
				.getApiService()
				.publishLhl(editTitle.getText().toString(), edit_des.getText().toString(), isPublic(), mLuoheTime.id, 1)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<LuoheBean>>(this) {
					@Override
					public void onSuccess(Result<LuoheBean> luoheBeanResult) {
						progressDialog.dismiss();
						if (luoheBeanResult.isHasReturnValidCode()) {
							ToastUtil.showToast(getString(R.string.send_success));
							EventBusControl.post(new Object(), EventBusControl.REFRESH_MY_LUOHE);
							finish();

						} else {
							ToastUtil.showToast(getString(R.string.send_fail));
						}
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						progressDialog.dismiss();
						ToastUtil.showToast(getString(R.string.send_fail));

					}
				});

	}

	private int isPublic() {
		return mIsPublic;
	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.write_luohe_activity;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_select_time:
			startActivity(new Intent(this, LuoheTimeAcitivity.class));
			break;
		}
	}

	@Subscriber(tag = EventBusControl.CHOICE_TIME)
	public void onEvent(LuoheTimeBean beanBusEvent) {
		tv_select_time.setText(beanBusEvent.name);
		mLuoheTime = beanBusEvent;
	}

}
