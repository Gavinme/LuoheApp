package com.luohe.android.luohe.find;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.luohe.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class FindRankCategoryFragment extends BaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;


    @Override
    protected void init(View view) {
        List<String> list = new ArrayList<>();
        list.add("生活&情感");
        list.add("生活&情感");
        list.add("生活&情感");
        list.add("生活&情感");

        List<Fragment> fragments = new ArrayList<>();
        for (String name : list) {
            Bundle bundle = new Bundle();
            bundle.putString("title", name);
            fragments.add(Fragment.instantiate(getActivity(), FindRankFragment.class.getName(), bundle));
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), fragments, list);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(list.size() - 1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);
    }

    protected int onBindLayoutId() {
        return R.layout.fragment_find_rank_category;
    }

}
