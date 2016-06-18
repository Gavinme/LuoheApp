package com.luohe.android.luohe.luohe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.net.model.LuoheWrapBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by GanQuan on 16/3/29.
 */
public class LuoheDetailActivity extends AppCompatActivity {
	@Bind(R.id.recycler_view)
	RecyclerView recyclerView;
	LuoheListFragment.LuoheListAdapter mAdapter;

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setTitle("主题列表");
		getTitlebar().setDefauleBackBtn();
		getTitlebar().setLeftText(getString(R.string.luohe));
		mAdapter = new LuoheListFragment.LuoheListAdapter(this);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(mAdapter);
		initList();
	}

	private void initList() {
		LuoheWrapBean luoheWrapBean = (LuoheWrapBean) getIntent().getSerializableExtra(ConstantsUtil.bean);
		List<LuoheWrapBean> list = new ArrayList<>();
		list.add(luoheWrapBean);
		if (luoheWrapBean.title != null && luoheWrapBean.title.size() > 0) {
			list.addAll(luoheWrapBean.getTitles(luoheWrapBean.userId));
		}
		mAdapter.initList(list);
	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.activity_detail_luohe;
	}

	public static Intent getStartIntent(Context context, LuoheWrapBean luoheWrapBean) {
		Intent it = new Intent(context, LuoheDetailActivity.class);
		it.putExtra(ConstantsUtil.bean, luoheWrapBean);
		return it;
	}
}
