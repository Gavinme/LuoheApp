package com.luohe.android.luohe.widget.viewpagerheadScroll.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.widget.viewpagerheadScroll.delegate.AbsListViewDelegate;
import com.luohe.android.luohe.widget.viewpagerheadScroll.delegate.ScrollViewDelegate;
import com.luohe.android.luohe.widget.viewpagerheadScroll.tools.ScrollableFragmentListener;
import com.luohe.android.luohe.widget.viewpagerheadScroll.tools.ScrollableListener;


public abstract class BaseScrollViewPagerFragment extends BaseFragment implements ScrollableListener {

	protected ScrollableFragmentListener mListener;
	protected static final String BUNDLE_FRAGMENT_INDEX = "BaseFragment.BUNDLE_FRAGMENT_INDEX";
	protected int mFragmentIndex;
	private AbsListViewDelegate mAbsListViewDelegate = new AbsListViewDelegate();
	private ScrollViewDelegate mScrollViewDelegate = new ScrollViewDelegate();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mFragmentIndex = bundle.getInt(BUNDLE_FRAGMENT_INDEX, 0);
		}

		if (mListener != null) {
			mListener.onFragmentAttached(this, mFragmentIndex);
		}
	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		View contentView = onBindDragView();
		if (contentView instanceof AbsListView) {
			return mAbsListViewDelegate.isViewBeingDragged(event, (AbsListView) onBindDragView());
		} else if (contentView instanceof ScrollView) {
			return mScrollViewDelegate.isViewBeingDragged(event, (ScrollView) onBindDragView());
		} else {
			throw new IllegalArgumentException("contentView is not illegal");
		}

	}

	protected abstract View onBindDragView();

	@Override
	public void onAttach(Activity activity) {
		Log.e("gq", "onAttach");
		super.onAttach(activity);
		try {
			mListener = (ScrollableFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement ScrollableFragmentListener");
		}

	}

	@Override
	public void onDetach() {
		Log.e("gq", "onDetach");
		if (mListener != null) {
			mListener.onFragmentDetached(this, mFragmentIndex);
		}

		super.onDetach();
		mListener = null;
	}
}
