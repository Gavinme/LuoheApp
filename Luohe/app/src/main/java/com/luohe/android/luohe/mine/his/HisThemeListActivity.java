package com.luohe.android.luohe.mine.his;

import com.luohe.android.luohe.base.FragmentHoldActivity;

/**
 * Created by GanQuan on 16/3/27.
 */
public class HisThemeListActivity extends FragmentHoldActivity<HisThemeListFragment> {
    private int userId;
    @Override
    protected Class<HisThemeListFragment> onGetFragmentClazz() {
        return HisThemeListFragment.class;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    protected void onSetArguments(HisThemeListFragment fragment) {

    }
}
