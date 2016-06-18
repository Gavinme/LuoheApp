package com.luohe.android.luohe.find;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.WishBean;
import com.luohe.android.luohe.utils.LocalDisplay;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.DividerDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * wish pool
 */
public class FindWishPoolFragment extends BaseFragment {
	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;

	@Bind(R.id.refresh_layout)
	SwipeRefreshLayout refreshLayout;
	private RankAdapter mListAdapter;
	private int mPage;

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
				mListAdapter.setOnItemClickLitener(new BaseRecyclerViewAdapter.OnItemClickListener<WishBean>() {
					@Override
					public void onItemClick(WishBean t, View view, int position) {
						WishBean bean = mListAdapter.getItemBean(position);
						if (bean.wishIsAccept == 0) {
							new WishOptDialog().show(getChildFragmentManager(), bean.wishIsAccept + "");
						} else {
							new WishOpt2Dialog().show(getChildFragmentManager(), bean.wishIsAccept + "");
						}
					}
				});
				getData();
			}
		});
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
		ApiLoader.getApiService().wishList(getArguments().getInt("type", 0), mPage).subscribeOn(getNewThread())
				.observeOn(getMainThread()).subscribe(new CommonSubscriber<Result<List<WishBean>>>(getActivity()) {
					@Override
					public void onSuccess(Result<List<WishBean>> listResult) {
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

	@BindLayout(id = R.layout.item_wish)
	static class ViewHolderRank extends BaseRecyclerViewAdapter.BaseViewHolder<WishBean> {

		@Bind(R.id.tv_from)
		TextView tvFrom;
		@Bind(R.id.tv_time)
		TextView tvTime;
		@Bind(R.id.tv_wish)
		TextView tvWish;
		@Bind(R.id.tv_status)
		TextView tvStatus;

		public ViewHolderRank(View itemView) {
			super(itemView);
		}

		@Override
		protected void bindView(WishBean bean, int position, Context context) {
			tvFrom.setText(Html.fromHtml(bean.userName + "赠<font color='#ff0000'>" + bean.wishValue + "</font>才值"));
			tvStatus.setText(bean.wishIsAccept == 0 ? "已接收" : "待处理");
			// noinspection deprecation
			tvStatus.setTextColor(context.getResources().getColor(bean.wishIsAccept == 0 ? R.color.green : R.color.red));
			tvTime.setText(bean.wishTime);
			tvWish.setText(bean.wishContent);
		}
	}

	public static class WishOptDialog extends DialogFragment {

		@NonNull
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
			dialog.setContentView(R.layout.activity_wish_menu);

			ButterKnife.bind(this, dialog);
			dialog.setCanceledOnTouchOutside(true);
			Window window = dialog.getWindow();
			WindowManager.LayoutParams params = window.getAttributes();
			params.width = LocalDisplay.SCREEN_WIDTH_PIXELS;
			params.y = (LocalDisplay.SCREEN_HEIGHT_PIXELS - params.height) / 2;
			window.setAttributes(params);
			window.setWindowAnimations(R.style.DialogWindowAnim);
			return dialog;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			ButterKnife.unbind(this);
		}

		@OnClick({ R.id.accept, R.id.ignor, R.id.reply, R.id.cancel })
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.accept:
				this.dismiss();
				break;
			case R.id.ignor:
				this.dismiss();
				break;
			case R.id.reply:
				this.dismiss();
				break;
			case R.id.cancel:
				this.dismiss();
				break;
			}
		}
	}

	public static class WishOpt2Dialog extends DialogFragment {

		@NonNull
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
			dialog.setContentView(R.layout.activity_wish_did_menu);

			ButterKnife.bind(this, dialog);
			dialog.setCanceledOnTouchOutside(true);
			Window window = dialog.getWindow();
			WindowManager.LayoutParams params = window.getAttributes();
			params.width = LocalDisplay.SCREEN_WIDTH_PIXELS;
			params.y = (LocalDisplay.SCREEN_HEIGHT_PIXELS - params.height) / 2;
			window.setAttributes(params);
			window.setWindowAnimations(R.style.DialogWindowAnim);
			return dialog;
		}

		@Override
		public void onDestroyView() {
			super.onDestroyView();
			ButterKnife.unbind(this);
		}

		@OnClick({ R.id.reply, R.id.cancel })
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.reply:
				this.dismiss();
				break;
			case R.id.cancel:
				this.dismiss();
				break;
			}
		}
	}

	private class RankAdapter extends BaseRecyclerViewAdapter<WishBean> {

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
