package com.luohe.android.luohe.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.common.TitleBar;

/**
 * Created by GanQuan on 16/3/27.
 */
public class MyLuoheListActivity extends FragmentHoldActivity<MyLuoheListFragment> {
	@Override
	protected void init(Bundle savedInstanceState) {
		super.init(savedInstanceState);
		getTitlebar().addAction(new TitleBar.TextAction("发布") {
			@Override
			public void performAction(View view) {
				startActivity(new Intent(MyLuoheListActivity.this, WriteLuoheActivity.class));

			}
		});
	}

	@Override
	protected Class<MyLuoheListFragment> onGetFragmentClazz() {
		return MyLuoheListFragment.class;
	}

	@Override
	protected void onSetArguments(MyLuoheListFragment fragment) {

	}
}
