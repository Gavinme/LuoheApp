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
import com.luohe.android.luohe.net.model.ReactBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;

/**
 * react
 */
public class FindReactFragment extends BaseFragment {
	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.refresh_layout)
	SwipeRefreshLayout refreshLayout;
	private RecommAdapter mLuoheListAdapter;

	@Override
	protected void init(View view) {
		initViews();
		initLoadingHelper(refreshLayout);
	}

	// private List<ReactBean> createData() {
	// List<ReactBean> list = new ArrayList<>();
	// for (int i = 0; i < 10; i++) {
	// list.add(new ReactBean());
	// }
	// return list;
	// }

	private void initViews() {
		mLuoheListAdapter = new RecommAdapter(getActivity());
		mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
		mRecyclerView.setAdapter(mLuoheListAdapter);
		mLuoheListAdapter.setOnItemClickLitener(new BaseRecyclerViewAdapter.OnItemClickListener<ReactBean>() {
			@Override
			public void onItemClick(ReactBean t, View view, int position) {
//				ReactBean recomBean = mLuoheListAdapter.getItemBean(position);
//				startActivity(new Intent(getActivity(), PreviewArticalActivity.class));
			}
		});
		// mLuoheListAdapter.addList(createData());
		mRecyclerView.postDelayed(new Runnable() {
			@Override
			public void run() {
				getData();
			}
		}, 200);
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
		ApiLoader.getApiService().eachOtherList(2 - getArguments().getInt("type", 0)).subscribeOn(getNewThread())
				.observeOn(getMainThread()).subscribe(new CommonSubscriber<Result<List<ReactBean>>>(getActivity()) {
					@Override
					public void onSuccess(Result<List<ReactBean>> listResult) {
						if (listResult != null && listResult.getResult() != null) {
							if (listResult.getResult().size() != 0) {
								getLoadingHelper().showContentView();
								mLuoheListAdapter.initList(listResult.getResult());
								mLuoheListAdapter.notifyDataSetChanged();
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

	@BindLayout(id = R.layout.recommen_list_item)
	static class ViewHolderReact extends BaseRecyclerViewAdapter.BaseViewHolder<ReactBean> {

		@Bind(R.id.avatar)
		ImageView avatar;
//		@Bind(R.id.state)
//		ImageView state;
//		@Bind(R.id.count)
//		TextView count;
		@Bind(R.id.name)
		TextView name;
		@Bind(R.id.time)
		TextView time;
		@Bind(R.id.title)
		TextView title;
		@Bind(R.id.desc)
		TextView desc;

		public ViewHolderReact(View itemView) {
			super(itemView);
		}

		@Override
		protected void bindView(ReactBean bean, int position, Context context) {
			ImageLoader.getInstance().displayImage(bean.headUrl, avatar);
			name.setText(bean.account);
			time.setText(bean.publishTime);
			title.setText(bean.sourceTitle);
			desc.setText(bean.artContent);
		}
	}

	private class RecommAdapter extends BaseRecyclerViewAdapter<ReactBean> {

		public RecommAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
			viewBundles.add(ViewHolderReact.class);
			// viewBundles.add(new ViewBundle(R.layout.recommen_list_item,
			// ViewHolderRecommend.class));
		}
	}
}
