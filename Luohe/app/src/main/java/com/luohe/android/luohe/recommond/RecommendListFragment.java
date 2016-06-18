package com.luohe.android.luohe.recommond;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplus.util.LayoutUtil;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.common.TitleViewPagerAdapter;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.service.RecommendListItem;
import com.luohe.android.luohe.net.model.LuoheTagBean;
import com.luohe.android.luohe.utils.TimeUtils;
import com.luohe.android.luohe.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/3/13.
 */
public class RecommendListFragment extends BaseRefreshListFragment<RecommendListItem> {
	private int tagId;

	@Override
	protected void onInit(View view, RecyclerView recyclerView) {
		super.onInit(view, recyclerView);
		recyclerView.setPadding(0, LayoutUtil.GetPixelByDIP(getActivity(), 8), 0, 0);
		LuoheTagBean tagBean = (LuoheTagBean) getArguments().getSerializable(TitleViewPagerAdapter.BUNDLE_KEY);
		if (tagBean != null)
			tagId = tagBean.baseId;
		Log.e("LuoheListFragment", "tagId:" + tagId);
		setListMode(MODE_DISABLE);
		refreshData();
		getLoadingHelper().showLoadingView();
		getLoadingHelper().addRetryListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshData();
			}
		});

	}

	@Override
	protected Class<? extends BaseRecyclerViewAdapter<RecommendListItem>> onGetAdapterType() {
		return RecommAdapter.class;

	}

	@Override
	protected void onPullDown(final AdapterManger<RecommendListItem> mAdapterManger) {
		ApiLoader.getApiService().recommendArticle(tagId, 0).subscribeOn(getNewThread()).observeOn(getMainThread())
				.subscribe(new CommonSubscriber<Result<List<RecommendListItem>>>(getActivity()) {
					@Override
					public void onSuccess(Result<List<RecommendListItem>> listResult) {
						if (listResult.getResult() != null && listResult.getResult() != null) {
							if (listResult.getResult().size() != 0) {
								getLoadingHelper().showContentView();
								mAdapterManger.initListToAdapter(listResult.getResult());
							} else {
								getLoadingHelper().showDefaultEmptyView();

							}
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

	@Override
	protected void onLoadMore(final AdapterManger<RecommendListItem> adapter) {
	}

	@Override
	protected void onListItemClick(RecommendListItem bean, View view, int adapterPosition) {
		if (bean.source == 0) {
			gotoLuoheArticle(bean);
		} else if (bean.source == 1) {
			gotoWenfeng(bean);
		}

	}

	private void gotoWenfeng(RecommendListItem bean) {
		startActivity(PreviewWenfengActivity.getStartIntent(getActivity(), bean.id));
	}

	private void gotoLuoheArticle(RecommendListItem bean) {
		startActivity(PreviewArticalActivity.getStartIntent(getActivity(), bean.id));
	}

	public static class RecommAdapter extends BaseRecyclerViewAdapter<RecommendListItem> {

		public RecommAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
			viewBundles.add(ViewHolderRecommend.class);
		}
	}

	@BindLayout(id = R.layout.recommen_list_item)
	static class ViewHolderRecommend extends BaseRecyclerViewAdapter.BaseViewHolder<RecommendListItem> {

		@Bind(R.id.avatar)
		ImageView avatar;
		// @Bind(R.id.state)
		// ImageView state;
		// @Bind(R.id.count)
		// TextView count;
		@Bind(R.id.name)
		TextView name;
		@Bind(R.id.time)
		TextView time;
		@Bind(R.id.title)
		TextView title;
		@Bind(R.id.desc)
		TextView desc;

		public ViewHolderRecommend(View itemView) {
			super(itemView);
		}

		@Override
		protected void bindView(final RecommendListItem bean, int position, final Context context) {
			time.setText(TimeUtils.getFormatTime(bean.publishTime + ""));
			name.setText(bean.nickName);
			desc.setText(bean.titleSubCon);
			title.setText(bean.titleName);

			// count.setText(bean.);todo
			// loadRoundImagUrl(avatar,bean.headUrl);
		}

	}
}
