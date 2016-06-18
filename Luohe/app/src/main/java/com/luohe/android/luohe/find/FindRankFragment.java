package com.luohe.android.luohe.find;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.RankBean;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.DividerDecoration;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Observable;

/**
 * rank
 */
public class FindRankFragment extends BaseFragment {
	public static final String CATEGORY = "category";
	public static final String TYPE = "type";

	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.refresh_layout)
	SwipeRefreshLayout refreshLayout;
	private RankAdapter mListAdapter;

	@Override
	protected void init(View view) {
		initViews();
		initLoadingHelper(refreshLayout);
		view.post(new Runnable() {
			@Override
			public void run() {
				mListAdapter = new RankAdapter(getActivity());
				mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
				mRecyclerView.addItemDecoration(new DividerDecoration(getActivity()));
				mRecyclerView.setAdapter(mListAdapter);
				mListAdapter.setOnItemClickLitener(new BaseRecyclerViewAdapter.OnItemClickListener<RankBean>() {
					@Override
					public void onItemClick(RankBean rankBean, View view, int position) {
						// RankBean bean = mListAdapter.getItemBean(position);
						// startActivity(new Intent(getActivity(),
						// PreviewArticalActivity.class));
					}

				});
				// mListAdapter.addList(createData());
			}
		});
	}

	private List<RankBean> createData() {
		List<RankBean> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(new RankBean());
		}
		return list;
	}

	private void initViews() {
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getData();
			}
		});
	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.find_detail_list;
	}

	public void getData() {
		refreshLayout.setRefreshing(true);
		Observable<Result<List<RankBean>>> rank;
		switch (getArguments().getInt(CATEGORY, 0)) {
			case 0:
				rank = ApiLoader.getApiService().rankByStyleId(getArguments().getInt("type", 0));
				break;
			case 1:
				rank = ApiLoader.getApiService().rankByStyleIdMain(getArguments().getInt("type", 0));
				break;
			case 2:
				rank = ApiLoader.getApiService().friendRankList(UserInfoUtil.getInstance().getUserId());
				break;
			default:
				rank = ApiLoader.getApiService().rankByStyleId(getArguments().getInt("type", 0));
				break;
		}
		rank.subscribeOn(getNewThread())
				.observeOn(getMainThread()).subscribe(new CommonSubscriber<Result<List<RankBean>>>(getActivity()) {
					@Override
					public void onSuccess(Result<List<RankBean>> listResult) {
						if (listResult != null && listResult.getResult() != null) {
							if (listResult.getResult().size() != 0) {
								getLoadingHelper().showContentView();
								mListAdapter.initList(listResult.getResult());
								mListAdapter.notifyDataSetChanged();
							} else {
								getLoadingHelper().showDefaultEmptyView();

							}
						} else {
							getLoadingHelper().showNetworkError();

						}
						refreshLayout.setRefreshing(false);

					}

				}.onError(new CommonSubscriber.ErrorHandler() {
					@Override
					public void onError(Throwable e) {
						getLoadingHelper().showNetworkError();
						refreshLayout.setRefreshing(false);

					}
				}));
	}

	@BindLayout(id = R.layout.item_rank)
	static class ViewHolderRank extends BaseRecyclerViewAdapter.BaseViewHolder<RankBean> {

		@Bind(R.id.tv_rank)
		TextView tvRank;
		@Bind(R.id.iv_avatar)
		ImageView ivAvatar;
		@Bind(R.id.tv_author)
		TextView tvAuthor;
		@Bind(R.id.tv_value)
		TextView tvValue;
		@Bind(R.id.tv_value_inc)
		TextView tvValueInc;

		public ViewHolderRank(View itemView) {
			super(itemView);
		}

		@Override
		protected void bindView(RankBean bean, int position, Context context) {
			tvRank.setText(bean.userLevel);
			ImageLoader.getInstance().displayImage(bean.headUrl, ivAvatar);
			tvAuthor.setText(bean.userName);
			tvValue.setText(String.format("%d", bean.moneyValue));
			tvValueInc.setText(String.format("%d", bean.growValueWeek));
		}
	}

	private class RankAdapter extends BaseRecyclerViewAdapter<RankBean> {

		public RankAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
			viewBundles.add(ViewHolderRank.class);
			// viewBundles.add(new ViewBundle(R.layout.recommen_list_item,
			// ViewHolderRecommend.class));
		}
	}
}
