package com.luohe.android.luohe.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.http.service.RecommendListItem;
import com.luohe.android.luohe.recommond.PreviewArticalActivity;
import com.luohe.android.luohe.recommond.RecommendListFragment;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: GanQuan Date: 16/5/26 Email:ganquan3640@gmail.com 文章搜索页
 */

public class RecommendListActivity extends FragmentHoldActivity<RecommendListActivity.WenFengListFragment> {
	@Override
	protected Class<WenFengListFragment> onGetFragmentClazz() {
		return WenFengListFragment.class;
	}

	@Override
	protected void onSetArguments(WenFengListFragment fragment) {
		Bundle bundle = new Bundle();
		bundle.putString(ConstantsUtil.key, getIntent().getStringExtra(ConstantsUtil.key));
		fragment.setArguments(bundle);

	}

	public static Intent getStartIntent(Context context, String key) {
		Intent it = new Intent(context, RecommendListActivity.class);
		it.putExtra(ConstantsUtil.key, key);
		return it;
	}

	public static class WenFengListFragment extends BaseRefreshListFragment<RecommendListItem> {
		@Override
		protected void init(View view) {
			super.init(view);
		}

		@Override
		protected void onInit(View view, RecyclerView recyclerView) {
			super.onInit(view, recyclerView);
			getTitleBar().setTitle("文风");
			getLoadingHelper().showLoadingView();
			refreshData();

		}

		@Override
		protected void onListItemClick(RecommendListItem bean, View view, int adapterPosition) {
			startActivity(PreviewArticalActivity.getStartIntent(getActivity(), bean.id));
		}

		@Override
		protected void onPullDown(final AdapterManger<RecommendListItem> mAdapterManger) {
			ApiLoader.getApiService().searchTitleList(getArguments().getString(ConstantsUtil.key))
					.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
					.subscribe(new CommonSubscriber<Result<List<RecommendListItem>>>(getActivity()) {
						@Override
						public void onSuccess(Result<List<RecommendListItem>> result) {
							getLoadingHelper().showContentView();
							if (result.getResult() != null) {

								mAdapterManger.initListToAdapter(result.getResult());
							} else {
								getLoadingHelper().showNetworkError();

							}
						}

						@Override
						public void onError(Throwable e) {
							super.onError(e);
							getLoadingHelper().showNetworkError();
						}
					});
		}

		@Override
		protected void onLoadMore(AdapterManger<RecommendListItem> adapter) {
		}

		@Override
		protected Class<? extends BaseRecyclerViewAdapter<RecommendListItem>> onGetAdapterType() {
			return RecommendListFragment.RecommAdapter.class;
		}
	}

}
