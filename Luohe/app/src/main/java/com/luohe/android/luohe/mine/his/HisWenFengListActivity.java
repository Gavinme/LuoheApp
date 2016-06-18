package com.luohe.android.luohe.mine.his;

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
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
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
public class HisWenFengListActivity extends FragmentHoldActivity<HisWenFengListActivity.HisWenFengListFragment> {
	private static int styleUserId;
	@Override
	protected Class<HisWenFengListFragment> onGetFragmentClazz() {
		return HisWenFengListFragment.class;
	}

	@Override
	protected void onSetArguments(HisWenFengListFragment fragment) {

	}

	public static class HisWenFengListFragment extends BaseRefreshListFragment<HisWenFengBean> {
		@Override
		protected void init(View view) {
			super.init(view);

		}

		@Override
		protected void onInit(View view, RecyclerView recyclerView) {
			super.onInit(view, recyclerView);
			getTitleBar().setTitle("TA的文风");
			getTitleBar().setDefauleBackBtn();
			getTitleBar().addAction(new TitleBar.TextAction("写文风") {
				@Override
				public void performAction(View view) {
					startActivity(new Intent(getActivity(), WriteWenfengActivity.class));
				}
			});
			getLoadingHelper().showLoadingView();
			styleUserId = getActivity().getIntent().getIntExtra("styleUserId",-23);
			refreshData();
		}

		@Override
		protected void onListItemClick(HisWenFengBean bean, View view, int adapterPosition) {
			startActivity(PreviewWenfengActivity.getStartIntent(getActivity(), bean.styleId));
		}

		@Override
		protected void onPullDown(final AdapterManger<HisWenFengBean> mAdapterManger) {
			ApiLoader.getApiService().hisStyleServices(styleUserId,0).subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new CommonSubscriber<Result<List<HisWenFengBean>>>(getActivity()) {
						@Override
						public void onSuccess(Result<List<HisWenFengBean>> result) {
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
		protected void onLoadMore(AdapterManger<HisWenFengBean> adapter) {
		}

		@Override
		protected Class<? extends BaseRecyclerViewAdapter<HisWenFengBean>> onGetAdapterType() {
			return WenFengListAdapter.class;
		}
	}

	public static class WenFengListAdapter extends BaseRecyclerViewAdapter<HisWenFengBean> {
		public WenFengListAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
			viewBundles.add(ViewHolderRecommend.class);
		}
	}

	@BindLayout(id = R.layout.item_my_wenfeng)
	static class ViewHolderRecommend extends BaseRecyclerViewAdapter.BaseViewHolder<HisWenFengBean> {

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
		protected void bindView(HisWenFengBean bean, int position, Context context) {
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

	public static class HisWenFengBean implements Serializable {
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
