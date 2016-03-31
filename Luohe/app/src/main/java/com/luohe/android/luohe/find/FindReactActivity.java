package com.luohe.android.luohe.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.luohe.FragmentAdapter;
import com.luohe.android.luohe.view.swipeback.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 互动页面
 */
public class FindReactActivity extends AppCompatActivity {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;


    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle(getString(R.string.find_react));
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("仅好友");

        List<Fragment> fragments = new ArrayList<>();
        for (String name : list) {
            Bundle bundle = new Bundle();
            bundle.putString("title", name);
            fragments.add(Fragment.instantiate(this, FindReactFragment.class.getName(), bundle));
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, list);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(list.size() - 1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);
    }

    protected int onBindLayoutId() {
        return R.layout.activity_find_react;
    }

    @OnClick({R.id.back, R.id.head_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.head_bg:
                break;
        }
    }
}
