package com.luohe.android.luohe.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.luohe.WriteThemeActivity;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.recommond.PreviewArticalActivity;
import com.luohe.android.luohe.recommond.PreviewWenfengActivity;
import com.luohe.android.luohe.recommond.WriteWenfengActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/3/27.
 */
public class MyWenFengListActivity extends FragmentHoldActivity<MyWenFengListActivity.MyWenFengListFragment> {

	@Override
	protected Class<MyWenFengListFragment> onGetFragmentClazz() {
		return MyWenFengListFragment.class;
	}

	@Override
	protected void onSetArguments(MyWenFengListFragment fragment) {

	}

	public static class MyWenFengListFragment extends BaseRefreshListFragment<WenFengBean> {
		@Override
		protected void init(View view) {
			super.init(view);

		}

		@Override
		protected void onInit(View view, RecyclerView recyclerView) {
			super.onInit(view, recyclerView);
			getTitleBar().setTitle("文风");
			getTitleBar().addAction(new TitleBar.TextAction("写文风") {
				@Override
				public void performAction(View view) {
					startActivity(new Intent(getActivity(), WriteWenfengActivity.class));
				}
			});
			getLoadingHelper().showLoadingView();
			refreshData();
		}

		@Override

		protected void onListItemClick(WenFengBean bean, View view, int adapterPosition) {
			startActivity(PreviewWenfengActivity.getStartIntent(getActivity(), bean.styleId));
		}

		@Override
		protected void onPullDown(final AdapterManger<WenFengBean> mAdapterManger) {
			ApiLoader.getApiService().myStyleServices(0).subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new CommonSubscriber<Result<List<WenFengBean>>>(getActivity()) {
						@Override
						public void onSuccess(Result<List<WenFengBean>> result) {
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
		protected void onLoadMore(AdapterManger<WenFengBean> adapter) {
		}

		@Override
		protected Class<? extends BaseRecyclerViewAdapter<WenFengBean>> onGetAdapterType() {
			return WenFengListAdapter.class;
		}
	}

	public static class WenFengListAdapter extends BaseRecyclerViewAdapter<WenFengBean> {
		public WenFengListAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
			viewBundles.add(ViewHolderRecommend.class);
		}
	}

	@BindLayout(id = R.layout.item_my_wenfeng)
	static class ViewHolderRecommend extends BaseRecyclerViewAdapter.BaseViewHolder<WenFengBean> {

		@Bind(R.id.title)
		TextView title;
		@Bind(R.id.tv_tag)
		TextView tvTag;
		@Bind(R.id.avatar)
		ImageView avatar;
		@Bind(R.id.name)
		TextView name;
		@Bind(R.id.time)
		TextView time;
		@Bind(R.id.desc)
		TextView desc;

		public ViewHolderRecommend(View itemView) {
			super(itemView);
		}

		@Override
		protected void bindView(WenFengBean bean, int position, Context context) {
			loadRoundImagUrl(avatar, bean.headUrl);
			name.setText(bean.userName);
			title.setText(bean.styleName);
			desc.setText(bean.getStyleSubContent());
			time.setText(bean.getPublishTime());
			if (bean.getStyleDraft() == 1) {
				tvTag.setVisibility(View.VISIBLE);
			} else {

				tvTag.setVisibility(View.GONE);
			}

		}
	}

	public static class WenFengBean implements Serializable {
		/**
		 * styleId : 173 headUrl :
		 * http://182.82.101.163/luohe/photo/defualtPhoto.jpg userName : ganquan
		 * userId : 72 publishTime : 2016-05-11 12:00:56 styleDraft : 1
		 */

		private int styleId;
		private String headUrl;
		private String userName;
		private int userId;
		private String publishTime;
		private int styleDraft;
		/**
		 * styleName : 16年03月08日(7) styleSubContent : 这里输入描述
		 */

		private String styleName;
		private String styleSubContent;

		public int getStyleId() {
			return styleId;
		}

		public void setStyleId(int styleId) {
			this.styleId = styleId;
		}

		public String getHeadUrl() {
			return headUrl;
		}

		public void setHeadUrl(String headUrl) {
			this.headUrl = headUrl;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getPublishTime() {
			return publishTime;
		}

		public void setPublishTime(String publishTime) {
			this.publishTime = publishTime;
		}

		public int getStyleDraft() {
			return styleDraft;
		}

		public void setStyleDraft(int styleDraft) {
			this.styleDraft = styleDraft;
		}

		public String getStyleName() {
			return styleName;
		}

		public void setStyleName(String styleName) {
			this.styleName = styleName;
		}

		public String getStyleSubContent() {
			return styleSubContent;
		}

		public void setStyleSubContent(String styleSubContent) {
			this.styleSubContent = styleSubContent;
		}
	}
}
