package com.luohe.android.luohe.recommond;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.common.ConstantsUtil;

/**
 * Created by GanQuan on 16/4/28.
 */
public class CommentListActivity extends FragmentHoldActivity<CommentListFragment> {
	@Override
	protected Class<CommentListFragment> onGetFragmentClazz() {
		return CommentListFragment.class;
	}

	@Override
	protected void onSetArguments(CommentListFragment fragment) {
		Bundle bundle = new Bundle();
		bundle.putInt(ConstantsUtil.id, getIntent().getIntExtra(ConstantsUtil.id, 0));
		fragment.setArguments(bundle);

	}

	public static Intent getStartIntent(Context context, int id) {
		Intent it = new Intent(context, CommentListActivity.class);
		it.putExtra(ConstantsUtil.id, id);
		return it;
	}
}
