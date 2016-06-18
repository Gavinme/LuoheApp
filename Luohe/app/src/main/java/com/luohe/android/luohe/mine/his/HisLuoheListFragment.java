package com.luohe.android.luohe.mine.his;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.luohe.LuoheListFragment;
import com.luohe.android.luohe.luohe.WriteThemeActivity;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.LuoheWrapBean;
import com.luohe.android.luohe.recommond.CommentListActivity;
import com.luohe.android.luohe.utils.ImageUtils;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/3/27.
 */
public class HisLuoheListFragment extends BaseRefreshListFragment<LuoheWrapBean> {
	private int userId;
	@Override
	protected void onInit(View view, RecyclerView recyclerView) {
		super.onInit(view, recyclerView);
		getTitleBar().setTitle("TA的落和");
		getTitleBar().setDefauleBackBtn();
		userId = getActivity().getIntent().getIntExtra("userId",-23);
		refreshData();
		getLoadingHelper().setEnable(true);
		getLoadingHelper().showLoadingView();

	}

	@Override
	protected Class<? extends BaseRecyclerViewAdapter<LuoheWrapBean>> onGetAdapterType() {
		return LuoheListFragment.LuoheListAdapter.class;
	}

	public static class MyluoheAdapter extends BaseRecyclerViewAdapter<LuoheWrapBean> {

		public MyluoheAdapter(Context context) {
			super(context);
		}

		@Override
		public int getItemViewType(int position) {
			return getItemBean(position).type;
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
			viewBundles.add(ViewHolderItem.class);
			viewBundles.add(VHInner.class);
		}

		@BindLayout(id = R.layout.luohe_adapter_item)
		static class ViewHolderItem extends BaseViewHolder<LuoheWrapBean> {
			@Bind(R.id.avatar)
			ImageView avatar;
			@Bind(R.id.state)
			TextView state;
			@Bind(R.id.name)
			TextView name;
			@Bind(R.id.time)
			TextView time;
			@Bind(R.id.divider)
			View divider;
			@Bind(R.id.title)
			TextView title;
			@Bind(R.id.desc)
			TextView desc;
			@Bind(R.id.commit)
			TextView commit;
			@Bind(R.id.luohe_divider)
			View luohe_divider;

			public ViewHolderItem(View itemView) {
				super(itemView);
			}

			@Override
			protected void bindView(final LuoheWrapBean bean, int position, final Context context) {
				if (position == 0) {
					luohe_divider.setVisibility(View.GONE);

				} else {
					luohe_divider.setVisibility(View.VISIBLE);
				}
				commit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						context.startActivity(WriteThemeActivity.getStartIntent(context, bean.id));

					}
				});
				commit.setVisibility(View.GONE);

				ImageUtils.displayRoundImage(bean.headUrl, avatar);
				state.setText(bean.getAwardTitle() + "");
				name.setText(bean.userName);
				time.setText(bean.getTime());
				title.setText(bean.fallOrderName);
				desc.setText(bean.fallOrderDes);

			}

		}

		@BindLayout(id = R.layout.luohe_inner_item)
		static class VHInner extends BaseViewHolder<LuoheWrapBean> {
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
			@Bind(R.id.tv_award)
			TextView tv_award;
			@Bind(R.id.tv_comment)
			TextView tv_comment;
			@Bind(R.id.tv_coin)
			TextView tvCoin;
			@Bind(R.id.divider)
			View divider;
			@Bind(R.id.left_icon)
			TextView leftIcon;

			public VHInner(View itemView) {
				super(itemView);
			}

			@Override
			protected void bindView(final LuoheWrapBean bean, int position, final Context context) {
				final LuoheWrapBean.SubjectBean subjectBean = bean.subjectBean;
				loadRoundImagUrl(avatar, subjectBean.headUrl);
				name.setText(subjectBean.userName);
				title.setText(subjectBean.titleName);
				desc.setText(subjectBean.titleDes);
				tvCoin.setText(subjectBean.artValue + "");
				tv_time.setText(subjectBean.getTime());
				tv_comment.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						context.startActivity(CommentListActivity.getStartIntent(context, subjectBean.titleId));
					}
				});
				if (subjectBean.pos == 0) {
					leftIcon.setText("必");
					leftIcon.setBackgroundResource(R.drawable.rect_blue_bound);
				} else {
					leftIcon.setText("");
					leftIcon.setBackgroundResource(R.drawable.luohe_rank_left);
				}

			}
		}

	}

	@Subscriber(tag = EventBusControl.REFRESH_MY_LUOHE)
	public void onEvent(Object object) {
		refreshData();
	}

	private List<LuoheWrapBean> handlerList(List<LuoheWrapBean> result) {

		List<LuoheWrapBean> list = new ArrayList<>();
		for (LuoheWrapBean luoheWrapBean : result) {
			list.add(luoheWrapBean);
			if (luoheWrapBean.getTitles(luoheWrapBean.userId) != null) {
				list.addAll(luoheWrapBean.getTitles(luoheWrapBean.userId));
			}
		}
		return list;
	}

	@Override
	protected void onPullDown(final AdapterManger<LuoheWrapBean> mAdapterManger) {
		getLoadingHelper().showLoadingView();
		ApiLoader.getApiService().hisFallOrder(userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<List<LuoheWrapBean>>>(getActivity()) {
					@Override
					public void onSuccess(Result<List<LuoheWrapBean>> listResult) {
						if (listResult != null && listResult.getResult() != null) {
							mAdapterManger.initListToAdapter(handlerList(listResult.getResult()));
						}

						getLoadingHelper().showContentView();
						getLoadingHelper().setEnable(false);
					}
				}.onError(new CommonSubscriber.ErrorHandler() {
					@Override
					public void onError(Throwable e) {
						getLoadingHelper().showNetworkError();
					}
				}));
	}

	@Override
	protected void onLoadMore(final AdapterManger<LuoheWrapBean> adapter) {

	}

}
