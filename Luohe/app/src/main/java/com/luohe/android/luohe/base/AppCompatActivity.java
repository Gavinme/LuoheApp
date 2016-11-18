package com.luohe.android.luohe.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.common.StatuBarManager;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.utils.LoadingHelper;

import butterknife.ButterKnife;

/**
 * Created by GanQuan on 16/3/5.带标题栏的activity 默认带有侧滑的效果
 */
public abstract class AppCompatActivity extends BaseActivity {
	TitleBar mTitlebar;
	protected RelativeLayout mRootView;
	FrameLayout mContentView;
	private LoadingHelper mLoadingHelper;
	protected boolean mSetTint = true;

	/**
	 * not use setContentView int this method,please return id in
	 * {@link #onBindLayoutId}
	 *
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_activity_layout);
//		if (mSetTint) {
//			StatuBarManager.setTintStyle(this);
//		}
		mRootView = (RelativeLayout) findViewById(R.id.root_view);
		mTitlebar = (TitleBar) findViewById(R.id.title_bar);
		mContentView = (FrameLayout) findViewById(R.id.content_view);
		int viewId = onBindLayoutId();
		View contentView = getLayoutInflater().inflate(viewId, null);

		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		mContentView.addView(contentView, lp);

		initBar(mTitlebar);
		butterKnifeBind();
		mLoadingHelper = new LoadingHelper(contentView);
		init(savedInstanceState);
		LogUtils.d(ConstantsUtil.app_msg, "go to activity:" + this.getLocalClassName());

	}

	public LoadingHelper getLoadingHelper() {
		return this.mLoadingHelper;
	}

	/**
	 * 初始化函数
	 *
	 * @param savedInstanceState
	 */
	protected abstract void init(Bundle savedInstanceState);

	/**
	 * 绑定view和当前activity中的实例变量，请勿使用setcontent去pass一个layoutId
	 *
	 * @return Id for contentView
	 */
	protected abstract int onBindLayoutId();

	private void initBar(TitleBar titleBar) {
		// titleBar.setImmersive(true);
		StatuBarManager.setImmersive(titleBar, mRootView);

		titleBar.setBackgroundColor(getResources().getColor(R.color.app_main_blue));

		// titleBar.setLeftImageResource(R.drawable.back_btn);
		// titleBar.setLeftText("返回");
		titleBar.setLeftTextColor(Color.WHITE);
		// set the default click listener
		titleBar.setLeftClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// titleBar.setTitle("落和令");
		titleBar.setTitleColor(Color.WHITE);
		titleBar.setSubTitleColor(Color.WHITE);
		titleBar.setDividerColor(Color.GRAY);
		titleBar.setActionTextColor(Color.WHITE);

	}

	protected TitleBar getTitlebar() {
		return this.mTitlebar;
	}

	protected void hideTitlebar() {
		this.mTitlebar.setVisibility(View.GONE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}
}
