package com.luohe.android.luohe.mine.his;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.mine.WriteLuoheActivity;

/**
 * Created by GanQuan on 16/3/27.
 */
public class HisLuoheListActivity extends FragmentHoldActivity<HisLuoheListFragment> {
	int userId;
	@Override
	protected void init(Bundle savedInstanceState) {
		super.init(savedInstanceState);
		userId = getIntent().getIntExtra("userId",-23);
		getTitlebar().addAction(new TitleBar.TextAction("发布") {
			@Override
			public void performAction(View view) {
				startActivity(new Intent(HisLuoheListActivity.this, WriteLuoheActivity.class));

			}
		});
	}

	public int getUserId() {
		return userId;
	}

	@Override
	protected Class<HisLuoheListFragment> onGetFragmentClazz() {
		return HisLuoheListFragment.class;
	}

	@Override
	protected void onSetArguments(HisLuoheListFragment fragment) {

	}
}
