package com.luohe.android.luohe.recommond;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.ReportBean;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.DividerDecoration;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: GanQuan Time: 16/3/30 Email:ganquan3640@gmail.com
 */
public class ReportClassActivity extends AppCompatActivity {
	@Bind(R.id.recycler_view)
	RecyclerView mRecycleView;
	ListAdapter mAdapter;

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setTitle("举报分类");
		getTitlebar().setDefauleBackBtn();
		mAdapter = new ListAdapter(this);
		mRecycleView.setLayoutManager(new LinearLayoutManager(this));
		mRecycleView.setAdapter(mAdapter);
		mRecycleView.addItemDecoration(new DividerDecoration(this));
		mAdapter.setOnItemClickLitener(new BaseRecyclerViewAdapter.OnItemClickListener<ReportBean>() {
			@Override
			public void onItemClick(ReportBean reportBean, View view, int position) {
				EventBusControl.post(reportBean, EventBusControl.SELECT_REPORT_TYPE);
				finish();
			}
		});
		loadData();
	}

	private void loadData() {
		getLoadingHelper().showLoadingView();
		ApiLoader.getApiService().comType().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<List<ReportBean>>>(this) {
					@Override
					public void onSuccess(Result<List<ReportBean>> result) {
						getLoadingHelper().showContentView();
						if (result.isHasReturnValidCode() && result.getResult() != null) {
							mAdapter.initList(result.getResult());
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
	protected int onBindLayoutId() {
		return R.layout.activity_report_class;
	}

	static class ListAdapter extends BaseRecyclerViewAdapter<ReportBean> {

		public ListAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> VhClazzList) {
			VhClazzList.add(VHClass.class);
		}
	}

	@BindLayout(id = R.layout.layout_string_item)
	static class VHClass extends BaseRecyclerViewAdapter.BaseViewHolder<ReportBean> {
		@Bind(R.id.tv_item)
		TextView tv_item;

		public VHClass(View itemView) {
			super(itemView);
		}

		@Override
		protected void bindView(ReportBean bean, int position, Context context) {
			tv_item.setText(bean.repName);
		}
	}
}
