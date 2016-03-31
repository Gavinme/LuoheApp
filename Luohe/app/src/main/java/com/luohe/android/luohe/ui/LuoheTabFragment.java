package com.luohe.android.luohe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.androidplus.util.LayoutUtil;
import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.Data.CubeApi;
import com.luohe.android.luohe.Data.CubeBean;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.common.RetrofitImp;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.luohe.CommonSearchActivity;
import com.luohe.android.luohe.luohe.FragmentAdapter;
import com.luohe.android.luohe.luohe.LuoheListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/2/20.
 */
public class LuoheTabFragment extends BaseFragment {
    String TAG = LuoheTabFragment.class.getSimpleName();

    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.search_view)
    TextView mSearchView;

    @OnClick(R.id.search_view)
    public void onSearch() {
        Intent it = new Intent(getActivity(), CommonSearchActivity.class);
        startActivity(it);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtils.e(TAG, "onattach" + activity.getClass().getSimpleName());
    }

    @Override
    protected void init(View view) {
        int px = LayoutUtil.GetPixelByDIP(getActivity(), 3);
        mTabLayout.setPadding(px, px, px, px);
        getTitleBar().setDividerHeight(0);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(ConstantsUtil.viewpager_title);
//            mTabLayout.addTab(mTabLayout.newTab().setText(list.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();
        for (String name : list) {
            Bundle bundle = new Bundle();
            bundle.putString("title", name);
            fragments.add(Fragment.instantiate(getActivity(), LuoheListFragment.class.getName(), bundle));

        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), fragments, list);
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.setOffscreenPageLimit(list.size() - 1);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.frgment_luohe_tab;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e(TAG, "onActivityCreated");
    }
}
