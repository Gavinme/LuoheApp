package com.luohe.android.luohe.find;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseActivity;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SendWishActivity extends BaseActivity {

	@Bind(R.id.title)
	TextView title;
	@Bind(R.id.right_button)
	TextView rightBtn;
	@Bind(R.id.edit_to)
	TextView editTo;
	@Bind(R.id.edit_content)
	EditText editContent;
	@Bind(R.id.edit_money)
	EditText editMoney;
	@Bind(R.id.ck_anonymous)
	CheckBox ckAnonymous;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_wish);
		ButterKnife.bind(this);
		initTitle();

	}

	private void initTitle() {
		title.setText(R.string.send_wish_act);
		rightBtn.setText(R.string.send);
		rightBtn.setBackgroundResource(R.drawable.rect_yellow_round);
		if (!TextUtils.isEmpty(getIntent().getStringExtra(ConstantsUtil.name))) {
			editTo.setText(getIntent().getStringExtra(ConstantsUtil.name));
		}

	}

	@OnClick({ R.id.back, R.id.right_button })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.right_button:
			sendWish();
			break;
		}
	}

	private void sendWish() {
		int coinCount = 0;
		if (!isStrEmpty(editMoney.getText().toString())) {
			coinCount = Integer.valueOf(editMoney.getText().toString());
		}

		int isAnnoy = ckAnonymous.isChecked() ? 1 : 0;
		ApiLoader
				.getApiService()
				.publishWish(getIntent().getIntExtra(ConstantsUtil.id, 0), coinCount, editContent.getText().toString(),
						isAnnoy).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result>(this) {
					@Override
					public void onSuccess(Result result) {
						if (result.isHasReturnValidCode()) {
							ToastUtil.showToast(getString(R.string.send_success));
							finish();
						} else {
							ToastUtil.showToast(getString(R.string.send_fail));
						}

					}
				}.onError(new CommonSubscriber.ErrorHandler() {
					@Override
					public void onError(Throwable e) {
						ToastUtil.showToast(getString(R.string.send_fail));
					}
				}));
		// DownLoader.getApiService().publishWish(tagId).subscribeOn(getNewThread()).observeOn(getMainThread())
		// .subscribe(new
		// CommonSubcriber<Result<List<LuoheWrapBean>>>(getprogressDialog()) {
		// @Override
		// public void onNext(Result<List<LuoheWrapBean>> listResult) {
		// }
		// });
	}

	public static Intent getStartIntent(Context context, int artId, String name) {
		Intent it = new Intent(context, SendWishActivity.class);
		it.putExtra(ConstantsUtil.id, artId);
		it.putExtra(ConstantsUtil.name, name);
		return it;
	}
}
