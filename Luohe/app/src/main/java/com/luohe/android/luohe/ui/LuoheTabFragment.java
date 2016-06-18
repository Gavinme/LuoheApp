package com.luohe.android.luohe.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.androidplus.util.LayoutUtil;
import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.base.LoadingErrorHandler;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.common.TitleViewPagerAdapter;
import com.luohe.android.luohe.luohe.CommonSearchActivity;
import com.luohe.android.luohe.luohe.LuoheListFragment;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.model.LuoheTagBean;
import com.luohe.android.luohe.utils.share.ShareHelper;
import com.luohe.android.luohe.utils.share.ShareItem;
import com.luohe.android.luohe.utils.share.ShareUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/2/20.
 */
public class LuoheTabFragment extends BaseFragment {
	String TAG = LuoheTabFragment.class.getSimpleName();

	@Bind(R.id.view_pager)
	ViewPager mViewPager;
	@Bind(R.id.tab_layout)
	TabLayout mTabLayout;
	@Bind(R.id.search_view)
	TextView mSearchView;

	@OnClick(R.id.search_view)
	public void onSearch() {
		Intent it = new Intent(getActivity(), CommonSearchActivity.class);
		startActivity(it);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtils.e(TAG, "onattach" + activity.getClass().getSimpleName());
	}

	private void loadTags() {
		initLoadingHelper(mViewPager).showLoadingView();
		getLoadingHelper().addRetryListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadTags();
			}
		});
		ApiLoader.getApiService().lhlStyle().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<List<LuoheTagBean>>>(getActivity()) {
					@Override
					public void onSuccess(Result<List<LuoheTagBean>> listResult) {

						if (listResult != null && listResult.getResult() != null && listResult.getResult().size() > 0) {
							List<LuoheTagBean> list = listResult.getResult();
							Log.e(TAG, list.toString());
							initViewPagers(list);
							getLoadingHelper().showContentView();
						} else {
							getLoadingHelper().showNetworkError();
						}

					}

				}.onError(new CommonSubscriber.ErrorHandler() {
					@Override
					public void onError(Throwable e) {
						getLoadingHelper().showNetworkError();
					}
				}));
	}

	void initViewPagers(List<LuoheTagBean> list) {
		TitleViewPagerAdapter fragmentAdapter = new TitleViewPagerAdapter(getChildFragmentManager(), list,
				LuoheListFragment.class);
		mViewPager.setAdapter(fragmentAdapter);
		mTabLayout.setupWithViewPager(mViewPager);
		mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
		mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);
	}

	@Override
	protected void init(View view) {

		int px = LayoutUtil.GetPixelByDIP(getActivity(), 3);
		mTabLayout.setPadding(px, px, px, px);
		getTitleBar().setDividerHeight(0);

		loadTags();

	}

	@Override
	public void onResume() {
		super.onResume();

	}


	@Override
	protected int onBindLayoutId() {
		return R.layout.frgment_luohe_tab;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtils.e(TAG, "onActivityCreated");
	}


}
