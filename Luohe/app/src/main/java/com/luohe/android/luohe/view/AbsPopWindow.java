package com.luohe.android.luohe.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.androidplus.util.LayoutUtil;

import butterknife.ButterKnife;

/**
 * Created by Gan Quan on 16/3/29.
 */
public abstract class AbsPopWindow {

	private PopupWindow mPopupWindow;
	private View mView;
	private Context mContext;

	public AbsPopWindow(Context context) {
		this.mContext = context;
		onCreateView();

	}

	public Context getContext() {
		return mContext;
	}

	protected abstract int getLayoutId();

	protected void onCreateView() {
		mView = View.inflate(mContext, getLayoutId(), null);
		mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
				true);

		mPopupWindow.setContentView(mView);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		ButterKnife.bind(this, mView);
	}

	public void showAsDropDown(View parent) {
		if (null != mPopupWindow) {
			mPopupWindow.showAsDropDown(parent);
		}
	}

	public void showAtLocation(View parent, int gravity, int x, int y) {
		if (null != mPopupWindow) {
			mPopupWindow.showAtLocation(parent, gravity, x, y);
		}
	}

	public void showUp(View view) {
		mView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		int width = mView.getMeasuredWidth();
		int height = mView.getMeasuredHeight();

		if (null != mPopupWindow) {
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAsDropDown(view, 0,
					-2 * view.getHeight() - height + LayoutUtil.GetPixelByDIP(mContext, 20));
		}
	}

	public void dismiss() {
		if (null != mPopupWindow && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}

}
