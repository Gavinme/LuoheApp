package com.luohe.android.luohe.test;

import android.os.Bundle;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.luohe.LuoheListFragment;
import com.luohe.android.luohe.view.swipeback.SwipeBackActivity;

/**
 * Created by GanQuan on 16/2/28.
 */
public class testActivity extends FragmentHoldActivity<LuoheListFragment> {

    @Override
    protected Class<LuoheListFragment> onGetFragmentClazz() {
        return LuoheListFragment.class;
    }

    @Override
    protected void onSetArguments(LuoheListFragment fragment) {

    }
}
