package com.luohe.android.luohe.find;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseActivity;
import com.luohe.android.luohe.luohe.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FindWishPoolActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.right_button)
    TextView rightBtn;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onBindLayoutId());
        ButterKnife.bind(this);

        title.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });

    }

    protected void init() {
        title.setText(R.string.find_wish_pool);
        rightBtn.setText(R.string.send_wish);
        rightBtn.setBackgroundResource(R.drawable.rect_white_trans_round);
        List<String> list = new ArrayList<>();
        list.add("接受的祝福");
        list.add("发出的祝福");

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            fragments.add(Fragment.instantiate(this, FindWishPoolFragment.class.getName(), bundle));
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

    @OnClick({R.id.back, R.id.right_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_button:
                startActivity(new Intent(view.getContext(), SendWishActivity.class));
                break;
        }
    }
}
