package com.luohe.android.luohe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.mine.AboutMeActivity;
import com.luohe.android.luohe.mine.MyLuoheListActivity;
import com.luohe.android.luohe.mine.MyThemeListActivity;
import com.luohe.android.luohe.mine.MyThemeListFragment;
import com.luohe.android.luohe.mine.SettingActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.luohe.android.luohe.find.FindItemView;

/**
 * Created by GanQuan on 16/2/20.
 */
public class MineTabFragment extends BaseFragment implements View.OnClickListener {
    String TAG = MineTabFragment.class.getSimpleName();
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.address_time)
    TextView addressTime;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.about_me)
    FindItemView aboutMe;
    @Bind(R.id.luohe)
    FindItemView luohe;
    @Bind(R.id.theme)
    FindItemView theme;
    @Bind(R.id.wenfeng)
    FindItemView wenfeng;
    @Bind(R.id.share)
    FindItemView share;
    @Bind(R.id.collect)
    FindItemView collect;
    @Bind(R.id.setting)
    FindItemView setting;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtils.e(TAG, "onattach" + activity.getClass().getSimpleName());
    }


    @Override
    protected int onBindLayoutId() {
        return R.layout.fragment_mine_tab;
    }


    @Override
    protected void init(View view) {
        initViews();
    }


    private void initViews() {

        aboutMe.setLeftTextview(R.drawable.my_about_user, getResources().getString(R.string.about_user));
        aboutMe.setOnClickListener(this);
        luohe.setLeftTextview(R.drawable.my_luohe, getResources().getString(R.string.luohe));
        luohe.setOnClickListener(this);
        theme.setLeftTextview(R.drawable.my_theme, getResources().getString(R.string.wenling));
        theme.setOnClickListener(this);
        wenfeng.setLeftTextview(R.drawable.my_wenfeng, getResources().getString(R.string.wenfeng));
        wenfeng.setOnClickListener(this);
        share.setLeftTextview(R.drawable.my_share, getResources().getString(R.string.share));
        share.setOnClickListener(this);
        collect.setLeftTextview(R.drawable.my_collect, getResources().getString(R.string.collect));
        collect.setOnClickListener(this);
        setting.setLeftTextview(R.drawable.my_setting, getResources().getString(R.string.setting));
        setting.setOnClickListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_me:

                startActivity(new Intent(getActivity(), AboutMeActivity.class));

                break;
            case R.id.luohe:
                startActivity(new Intent(getActivity(), MyLuoheListActivity.class));
                break;
            case R.id.theme:
                startActivity(new Intent(getActivity(), MyLuoheListActivity.class));
                break;
            case R.id.wenfeng:
                startActivity(new Intent(getActivity(), MyLuoheListActivity.class));
                break;
            case R.id.share:
                break;
            case R.id.collect:
                break;
            case R.id.setting:

                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }
}
