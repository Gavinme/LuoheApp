package com.luohe.android.luohe.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.find.FindItemView;
import com.luohe.android.luohe.mine.AboutMeActivity;
import com.luohe.android.luohe.mine.MyCollectListActivity;
import com.luohe.android.luohe.mine.MyLuoheListActivity;
import com.luohe.android.luohe.mine.MyShareListActivity;
import com.luohe.android.luohe.mine.MyThemeListActivity;
import com.luohe.android.luohe.mine.SettingActivity;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.user.UserCommonInfo;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.ImageUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    @Bind(R.id.cash)
    FindItemView cash;

    @Bind(R.id.container)
    ScrollView container;

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
        initLoadingHelper(container);
        getLoadingHelper().addRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadInfo();
            }
        });
        loadInfo();
    }

    private void loadInfo() {
        getLoadingHelper().showLoadingView();
        ApiLoader.getApiService().userInfo(UserInfoUtil.getInstance().getUserInfo().getUid())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<Result<UserCommonInfo>>(getActivity()) {
                    @Override
                    public void onSuccess(Result<UserCommonInfo> userInfoResult) {
                        if (userInfoResult.getResult() != null) {
                            getLoadingHelper().showContentView();
                            UserInfoUtil.getInstance().updateUserInfo(userInfoResult.getResult());
                            setUserViews(userInfoResult.getResult());
                        } else {
                            getLoadingHelper().showNetworkError();
                        }
                    }

                }.onError(new CommonSubscriber.ErrorHandler() {
                    @Override
                    public void onError(Throwable e) {
                        getLoadingHelper().showNetworkError();
                    }
                }));
    }

    private void setUserViews(UserCommonInfo result) {
        ImageUtils.displayRoundImage(result.headUrl, avatar);
        name.setText(result.nickName);
        if (!TextUtils.isEmpty(result.birthday) && !TextUtils.isEmpty(result.province)) {
            addressTime.setText(String.format("%s|%s", result.birthday, result.province));
        } else {
            addressTime.setText("");
        }
        if (!TextUtils.isEmpty(result.accountDesc)) {
            desc.setText(result.accountDesc);
        }
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
        cash.setLeftTextview(R.drawable.my_about_user, "提现");
        cash.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
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
                Intent aboutMeIntent = new Intent(getActivity(),AboutMeActivity.class);
                aboutMeIntent.putExtra("uid", UserInfoUtil.getInstance().getUserInfo().getUid());
                startActivity(aboutMeIntent);
                break;
            case R.id.luohe:
                startActivity(new Intent(getActivity(), MyLuoheListActivity.class));
                break;
            case R.id.theme:// 我的主题
                startActivity(new Intent(getActivity(), MyThemeListActivity.class));
                break;
            case R.id.wenfeng:
                startActivity(new Intent(getActivity(), MyWenFengListActivity.class));
                break;
            case R.id.share:
                startActivity(new Intent(getActivity(),MyShareListActivity.class));
                break;
            case R.id.collect:
                startActivity(new Intent(getActivity(), MyCollectListActivity.class));
                break;
            case R.id.setting:

                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.cash:// todo
                startActivity(new Intent(getActivity(), CashActivity.class));
                break;
        }
    }
}
