package com.luohe.android.luohe.luohe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.user.UserCommonInfo;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: GanQuan Date: 16/5/26 Email:ganquan3640@gmail.com
 */

public class UserListFragment extends BaseRefreshListFragment<UserCommonInfo> {
	@Override
	protected void onInit(View view, RecyclerView recyclerView) {
		super.onInit(view, recyclerView);
		getTitleBar().setTitle("用户");setListMode(MODE_DISABLE);
		getLoadingHelper().showLoadingView();
		refreshData();

	}


	@Override
	protected void onPullDown(final AdapterManger<UserCommonInfo> mAdapterManger) {
		ApiLoader.getApiService().searchList(getArguments().getString(ConstantsUtil.key)).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<List<UserCommonInfo>>>(getActivity()) {
					@Override
					public void onSuccess(Result<List<UserCommonInfo>> result) {
						getLoadingHelper().showContentView();
						if (result.isHasReturnValidCode() && result.getResult() != null
								&& result.getResult().size() > 0) {
							mAdapterManger.initListToAdapter(result.getResult());
						} else {
							getLoadingHelper().showNetworkError();
						}
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
					}
				});
	}

	@Override
	protected void onLoadMore(AdapterManger<UserCommonInfo> adapter) {

	}

	@Override
	protected Class<? extends BaseRecyclerViewAdapter<UserCommonInfo>> onGetAdapterType() {
		return UserListAdapter.class;
	}

	public static class UserListAdapter extends BaseRecyclerViewAdapter<UserCommonInfo> {
		public UserListAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> VhClazzList) {
			VhClazzList.add(userViewHolder.class);
		}
	}

	@BindLayout(id = R.layout.item_layout_user)
	public static class userViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<UserCommonInfo> {
		@Bind(R.id.avatar)
		ImageView avatar;
		@Bind(R.id.name)
		TextView name;

		public userViewHolder(View itemView) {
			super(itemView);
		}

		@Override
		protected void bindView(UserCommonInfo bean, int position, Context context) {
			loadRoundImagUrl(avatar, bean.headUrl);
			name.setText(bean.nickName);
		}
	}
}
