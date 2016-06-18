package com.luohe.android.luohe.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.common.listview.BaseListViewAdapter;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.LuoheTimeBean;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/4/23.
 */
public class LuoheTimeAcitivity extends AppCompatActivity {
	@Bind(R.id.listview)
	ListView mListView;

	ListAdapter mAdapter;

	@Override
	protected void init(Bundle savedInstanceState) {
		mAdapter = new ListAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				LuoheTimeBean bean = (LuoheTimeBean) parent.getAdapter().getItem(position);
				EventBusControl.post(bean, EventBusControl.CHOICE_TIME);
				finish();
			}
		});
		loadData();

	}

	private void loadData() {
		getLoadingHelper().showLoadingView();
		ApiLoader.getApiService().fallOrderTime().subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<List<LuoheTimeBean>>>(this) {
					@Override
					public void onSuccess(Result<List<LuoheTimeBean>> listResult) {
						if (listResult != null && listResult.getResult() != null) {
							mAdapter.initList(listResult.getResult());
							getLoadingHelper().showContentView();
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
	protected int onBindLayoutId() {
		return R.layout.activity_time_luohe;
	}

	static class ListAdapter extends BaseListViewAdapter<LuoheTimeBean> {

		public ListAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindViewHolder(List<ViewBundle> list) {
			list.add(new ViewBundle(R.layout.item_luohe_time, ViewHolder.class));

		}
	}

	static class ViewHolder extends BaseListViewAdapter.BaseListViewHolder<LuoheTimeBean> {
		@Bind(R.id.tv_time)
		TextView tv_time;

		@Override
		protected void setView(LuoheTimeBean bean, Context context) {
			tv_time.setText(bean.name);
		}
	}
}
