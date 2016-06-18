package com.luohe.android.luohe.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.net.model.LuoheTagBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 带title的adapter
 */
public class TitleViewPagerAdapter extends FragmentStatePagerAdapter {
    public final static int HUIZONG = 0;
    public final static String BUNDLE_KEY = "tag";
    private List<LuoheTagBean> mTags = new ArrayList<>();
    private Class<? extends BaseFragment> mFragmentClass;
    public TitleViewPagerAdapter(FragmentManager fm, List<LuoheTagBean> titleList, Class<? extends BaseFragment> fragmentClass) {
        super(fm);
        this.mFragmentClass = fragmentClass;
        if (titleList != null && titleList.size() != 0) {
        LuoheTagBean luoheTagBean = new LuoheTagBean();
            luoheTagBean.setBaseId(HUIZONG);
            luoheTagBean.setBaseName("汇总");
            mTags.add(luoheTagBean);
            mTags.addAll(titleList);
        }
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_KEY, mTags.get(i));
        return Fragment.instantiate(MyApplication.getContext(), mFragmentClass.getName(), bundle);
    }

    @Override
    public String getPageTitle(int position) {
        return mTags.size() == 0 ? "" : mTags.get(position).baseName;
    }

    @Override
    public int getCount() {
        return mTags.size();
    }

}
