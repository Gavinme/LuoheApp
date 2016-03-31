package com.luohe.android.luohe.mine;

import com.luohe.android.luohe.base.FragmentHoldActivity;

/**
 * Created by GanQuan on 16/3/27.
 */
public class MyLuoheListActivity extends FragmentHoldActivity<MyLuoheListFragment> {
    @Override
    protected Class<MyLuoheListFragment> onGetFragmentClazz() {
        return MyLuoheListFragment.class;
    }

    @Override
    protected void onSetArguments(MyLuoheListFragment fragment) {

    }
}
