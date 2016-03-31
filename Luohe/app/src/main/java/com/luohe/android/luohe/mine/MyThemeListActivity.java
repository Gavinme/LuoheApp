package com.luohe.android.luohe.mine;

import com.luohe.android.luohe.base.FragmentHoldActivity;

/**
 * Created by GanQuan on 16/3/27.
 */
public class MyThemeListActivity extends FragmentHoldActivity<MyThemeListFragment> {
    @Override
    protected Class<MyThemeListFragment> onGetFragmentClazz() {
        return MyThemeListFragment.class;
    }

    @Override
    protected void onSetArguments(MyThemeListFragment fragment) {

    }
}
