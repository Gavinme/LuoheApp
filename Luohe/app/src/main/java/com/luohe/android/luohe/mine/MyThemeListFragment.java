package com.luohe.android.luohe.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.LuoheWrapBean;
import com.luohe.android.luohe.recommond.CommentListActivity;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/3/20.我的问令
 */
public class MyThemeListFragment extends BaseRefreshListFragment<LuoheWrapBean.SubjectBean> {

	@Override
	protected void onInit(View container, RecyclerView recyclerView) {
		super.onInit(container, recyclerView);
		getTitleBar().setDefauleBackBtn();
		getTitleBar().setTitle("我的问令");
		setListMode(MODE_DISABLE);
		refreshData();
		getLoadingHelper().showLoadingView();
	}

	@Override
	protected void onPullDown(final AdapterManger<LuoheWrapBean.SubjectBean> mAdapterManger) {
		ApiLoader.getApiService().mySubject(0).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<List<LuoheWrapBean.SubjectBean>>>(getActivity()) {
					@Override
					public void onSuccess(Result<List<LuoheWrapBean.SubjectBean>> result) {
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
	protected void onLoadMore(final AdapterManger<LuoheWrapBean.SubjectBean> adapter) {
	}

	@Override
	protected Class<? extends BaseRecyclerViewAdapter<LuoheWrapBean.SubjectBean>> onGetAdapterType() {
		return RecommAdapter.class;
	}

	static class RecommAdapter extends BaseRecyclerViewAdapter<LuoheWrapBean.SubjectBean> {

		public RecommAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
			viewBundles.add(VHInner.class);
		}
	}

	@BindLayout(id = R.layout.luohe_inner_myitem)
	static class VHInner extends BaseRecyclerViewAdapter.BaseViewHolder<LuoheWrapBean.SubjectBean> {
		@Bind(R.id.avatar)
		ImageView avatar;
		@Bind(R.id.name)
		TextView name;
		@Bind(R.id.title)
		TextView title;
		@Bind(R.id.desc)
		TextView desc;
		@Bind(R.id.tv_time)
		TextView tv_time;
		@Bind(R.id.tv_see_article)
		TextView tv_see_article;
		@Bind(R.id.layout_bottom)
		RelativeLayout layoutBottom;
		@Bind(R.id.left_icon)
		TextView leftIcon;
		@Bind(R.id.tv_coin)
		TextView tvCoin;
		@Bind(R.id.content_layout)
		RelativeLayout contentLayout;
		@Bind(R.id.tv_comment)
		TextView tvComment;

		public VHInner(View itemView) {
			super(itemView);
		}

		@Override
		protected void bindView(final LuoheWrapBean.SubjectBean bean, int position, final Context context) {
			loadRoundImagUrl(avatar, bean.headUrl);
			name.setText(bean.userName);
			title.setText(bean.artTitle);
			desc.setText(bean.artDes);
			tvCoin.setText(bean.artValue + "");
			tv_time.setVisibility(View.GONE);
			tv_see_article.setVisibility(View.VISIBLE);
			if (bean.artOrder == 1) {
				leftIcon.setText("必");
				leftIcon.setBackgroundResource(R.drawable.rect_blue_bound);

			} else {
				leftIcon.setText("");
				leftIcon.setBackgroundResource(R.drawable.luohe_rank_left);

			}
			tvComment.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					context.startActivity(CommentListActivity.getStartIntent(context, bean.artId));
				}
			});

		}
	}

}
