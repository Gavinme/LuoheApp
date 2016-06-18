package com.luohe.android.luohe.luohe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.common.ConstantsUtil;

/**
 * Author: GanQuan Date: 16/5/26 Email:ganquan3640@gmail.com
 */

public class UserListActivity extends FragmentHoldActivity<UserListFragment> {
	@Override
	protected Class<UserListFragment> onGetFragmentClazz() {
		return UserListFragment.class;
	}

	@Override
	protected void onSetArguments(UserListFragment fragment) {
		Bundle bundle = new Bundle();
		bundle.putString(ConstantsUtil.key, getIntent().getStringExtra(ConstantsUtil.key));
		fragment.setArguments(bundle);

	}

	public static Intent getStartIntent(Context context, String key) {
		Intent it = new Intent(context, UserListActivity.class);
		it.putExtra(ConstantsUtil.key, key);
		return it;
	}
}
