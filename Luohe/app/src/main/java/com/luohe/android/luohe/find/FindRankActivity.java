package com.luohe.android.luohe.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.luohe.FragmentAdapter;
import com.luohe.android.luohe.view.swipeback.SwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindRankActivity extends SwipeBackActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;


    /**
     * not use setContentView int this method,please return id in{@link #onBindLayoutId}
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onBindLayoutId());
        ButterKnife.bind(this);

        butterKnifeBind();
        title.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });

    }

    protected void init() {
        title.setText(R.string.find_rank);
        List<String> list = new ArrayList<>();
        list.add("累计才值榜");
        list.add("每周百强榜");
        list.add("好友榜");

        List<Fragment> fragments = new ArrayList<>();
        for (String name : list) {
            Bundle bundle = new Bundle();
            bundle.putString("title", name);
            fragments.add(Fragment.instantiate(this, FindRankCategoryFragment.class.getName(), bundle));
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, list);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(list.size() - 1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);
    }

    protected int onBindLayoutId() {
        return R.layout.activity_find_rank;
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
