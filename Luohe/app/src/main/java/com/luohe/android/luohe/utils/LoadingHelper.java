package com.luohe.android.luohe.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.luohe.android.luohe.R;

/**
 * Created by GanQuan on 16/4/11.
 */
public class LoadingHelper {
	private View mContentView;
	private Context mContext;
	public final String TAG = LoadingHelper.class.getSimpleName();

	private LoadingView mLoadingView;
	private RetryView mRetryView;
	private boolean mEnable = true;

	public void addRetryListener(View.OnClickListener retryClick) {
		mRetryView.setOnClickListener(retryClick);
	}

	public void removeRetryListener() {
		mRetryView = null;
	}

	public LoadingHelper(View content) {
		onCreateView(content);
	}

	/**
	 * @param content
	 */
	public LoadingHelper onCreateView(View content) {
		mContext = content.getContext();
		ViewGroup parent = (ViewGroup) content.getParent();
		addViews(parent, parent.indexOfChild(content));
		return this;
	}

	private void addViews(ViewGroup parent, int position) {
		mContentView = parent.getChildAt(position);

		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		mLoadingView = new LoadingView(mContext);
		mRetryView = new RetryView(mContext);
		parent.addView(mLoadingView, position, params);
		mLoadingView.setVisibility(View.GONE);// added by gq
		parent.addView(mRetryView, position, params);
		mRetryView.setVisibility(View.GONE);
	}

	public boolean isRetryViewVisible() {
		return mRetryView.getVisibility() == View.VISIBLE;
	}

	public void setEnable(boolean enable) {
		this.mEnable = enable;
	}

	public void showContentView() {
		if (!mEnable)
			return;
		Log.d(TAG, "showContentView: ");
		mLoadingView.setVisibility(View.GONE);
		mRetryView.setVisibility(View.GONE);
		mContentView.setVisibility(View.VISIBLE);
	}

	public void showLoadingView() {
		if (!mEnable)
			return;
		Log.d(TAG, "showLoadingView: ");
		mContentView.setVisibility(View.GONE);
		mRetryView.setVisibility(View.GONE);
		mLoadingView.setVisibility(View.VISIBLE);

	}

	public void showNetworkError() {
		if (!mEnable)
			return;
		Log.d(TAG, "showNetworkError: ");
		mLoadingView.setVisibility(View.GONE);
		mContentView.setVisibility(View.GONE);
		mRetryView.setVisibility(View.VISIBLE);
	}

	public void showDefaultEmptyView() {
		if (!mEnable)
			return;
		mRetryView.setVisibility(View.VISIBLE);
		mRetryView.setTipText("没有数据哦");
		mLoadingView.setVisibility(View.GONE);
		mContentView.setVisibility(View.GONE);
	}

	static class LoadingView extends BaseViewWrap {
		private ProgressBar mProgress;
		private TextView mMessageView;

		public LoadingView(Context context) {
			super(context);
		}

		public LoadingView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
		}

		@Override
		protected void onViewCreated(View mView) {
			mProgress = (ProgressBar) mView.findViewById(R.id.progress_bar);
			mMessageView = (TextView) mView.findViewById(R.id.message);
		}

		@Override
		protected int onBindLayoutId() {
			return R.layout.common_loading_layout;
		}

	}

	static class RetryView extends BaseViewWrap {
		private ImageView mRetryTipImg;
		private TextView mRetryTipTv;

		public RetryView(Context context) {
			super(context);
		}

		public RetryView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public RetryView(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
		}

		@Override
		protected void onViewCreated(View mView) {
			mRetryTipTv = (TextView) mView.findViewById(R.id.tv_tip);
			mRetryTipImg = (ImageView) mView.findViewById(R.id.img_retry_btn);
			mRetryTipTv.setText(R.string.str_no_net);
		}

		public void setTipText(String string) {
			mRetryTipTv.setText(string);
		}

		@Override
		protected int onBindLayoutId() {
			return R.layout.common_retry_layout;
		}

		public void setOnRetryListener(OnClickListener onClickListener) {
			this.mRetryTipImg.setOnClickListener(onClickListener);
		}

	}
}
