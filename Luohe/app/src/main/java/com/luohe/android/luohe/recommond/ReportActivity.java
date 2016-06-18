package com.luohe.android.luohe.recommond;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.ReportBean;
import com.luohe.android.luohe.utils.ToastUtil;

import org.simple.eventbus.Subscriber;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: GanQuan Time: 16/3/30 Email:ganquan3640@gmail.com
 */
public class ReportActivity extends AppCompatActivity {
	@Bind(R.id.tv_report_name)
	TextView tv_report_name;
	@Bind(R.id.edit_des)
	EditText edit_des;
	int report_type = 1;
	String report_name = "广告";
	String report_des = "";
	int mId = 0;
	int mType = 1;// 1文章 2评论

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setDefauleBackBtn();
		getTitlebar().setTitle(getString(R.string.report));
		getTitlebar().addAction(new TitleBar.TextAction("发布") {
			@Override
			public void performAction(View view) {
				ApiLoader.getApiService().comArtOrCom(mId, mType, report_type, report_des).subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new CommonSubscriber<Result>(ReportActivity.this) {
							@Override
							public void onSuccess(Result result) {
								finish();
								ToastUtil.showToast(getString(R.string.report_suc));
							}
						}.onError(new CommonSubscriber.ErrorHandler() {
							@Override
							public void onError(Throwable e) {
								ToastUtil.showToast(getString(R.string.report_fail));
							}
						}));
			}

			@Override
			public int getBackground() {
				return R.drawable.rect_white_frame;
			}
		});
		tv_report_name.setText(report_name);
		tv_report_name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getContext(), ReportClassActivity.class));
			}
		});

	}

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

	@Subscriber(tag = EventBusControl.SELECT_REPORT_TYPE)
	public void onEvent(ReportBean bean) {
		report_name = bean.repName;
		report_type = bean.repTypeId;
		tv_report_name.setText(report_name);

	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.activity_report_detail;
	}

}
