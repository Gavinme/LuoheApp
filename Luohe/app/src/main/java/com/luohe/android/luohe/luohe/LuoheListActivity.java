package com.luohe.android.luohe.luohe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.common.ConstantsUtil;

/**
 * Author: GanQuan Time: 16/5/7 Email:ganquan3640@gmail.com
 */

public class LuoheListActivity extends FragmentHoldActivity<LuoheListFragment> {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getTitlebar().setTitle("落和");
		getTitlebar().setDefauleBackBtn();
	}

	@Override
	protected Class<LuoheListFragment> onGetFragmentClazz() {
		return LuoheListFragment.class;
	}

	@Override
	protected void onSetArguments(LuoheListFragment fragment) {
		fragment.setSearchArgument(getIntent().getStringExtra(ConstantsUtil.key));
	}

	public static Intent getStartIntent(Context context, String key) {
		Intent it = new Intent(context, LuoheListActivity.class);
		it.putExtra(ConstantsUtil.key, key);
		return it;
	}
}
