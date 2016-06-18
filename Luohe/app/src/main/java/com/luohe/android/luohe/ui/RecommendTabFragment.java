package com.luohe.android.luohe.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.androidplus.util.LayoutUtil;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.common.TitleViewPagerAdapter;
import com.luohe.android.luohe.luohe.CommonSearchActivity;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.model.LuoheTagBean;
import com.luohe.android.luohe.recommond.RecommendListFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/2/20.
 */
public class RecommendTabFragment extends BaseFragment {
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
				RecommendListFragment.class);
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

}
