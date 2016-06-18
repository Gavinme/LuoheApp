package com.luohe.android.luohe.mine;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.find.FindItemView;
import com.luohe.android.luohe.mine.his.HisLuoheListActivity;
import com.luohe.android.luohe.mine.his.HisThemeListActivity;
import com.luohe.android.luohe.mine.his.HisWenFengListActivity;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.ui.MyWenFengListActivity;
import com.luohe.android.luohe.user.UserCommonInfo;
import com.luohe.android.luohe.utils.ImageUtils;
import com.luohe.android.luohe.utils.ToastUtil;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TACityFallActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.sexy_img)
    ImageView sexyImg;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.address_time)
    TextView addressTime;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.attention)
    TextView attention;
    @Bind(R.id.chat)
    TextView chat;
    @Bind(R.id.send_wish)
    TextView sendWish;
    @Bind(R.id.about_me)
    FindItemView aboutMe;
    @Bind(R.id.luohe)
    FindItemView luohe;
    @Bind(R.id.theme)    //问令
            FindItemView theme;
    @Bind(R.id.wenfeng)
    FindItemView wenfeng;
    @Bind(R.id.share)
    FindItemView share;
    @Bind(R.id.container)
    ScrollView container;
    int userId;

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setDefauleBackBtn();
        getTitlebar().setTitle("TA的城落");
        initViews();
        setOnclicks();
    }

    private void setOnclicks() {
        aboutMe.setOnClickListener(this);
        luohe.setOnClickListener(this);
        wenfeng.setOnClickListener(this);
        share.setOnClickListener(this);
        chat.setOnClickListener(this);
        theme.setOnClickListener(this);

    }

    private void initViews() {
        aboutMe.setLeftTextview(R.drawable.my_about_user, "关于才者");
        luohe.setLeftTextview(R.drawable.my_luohe, "落和令");
        theme.setLeftTextview(R.drawable.my_theme, "问令");
        wenfeng.setLeftTextview(R.drawable.my_wenfeng, "文风");
        share.setLeftTextview(R.drawable.my_share, "分享");
        userId = getIntent().getIntExtra("userId", -1);
        queryUserInfo(userId);
    }

    private void queryUserInfo(int userId) {
        ApiLoader.getApiService().userInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<Result<UserCommonInfo>>(this) {
                    @Override
                    public void onSuccess(Result<UserCommonInfo> result) {
                        addUserInfo(result);
                    }
                });
    }

    private void addUserInfo(Result<UserCommonInfo> result) {
        if (result.isHasReturnValidCode() && result.getResult() != null) {
            LogUtils.d("fxl", result.getResult() + toString());
            ImageUtils.displayRoundImage(result.getResult().headUrl, avatar);
            name.setText(result.getResult().nickName);
            addressTime.setText(result.getResult().birthday + "|" + result.getResult().province);
            desc.setText(result.getResult().accountDesc);
            if (result.getResult().sex.equals("s")) {
                sexyImg.setImageResource(R.drawable.icon_sexy);
            } else {
                sexyImg.setImageResource(R.drawable.icon_sexx);
            }
            if (result.getResult().isFocus == 1) {
                attention.setText("已关注");
            }
        }

    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_tacity_fall;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.avatar:
                ToastUtil.showToast("抱歉，暂未开放此功能");
                break;
            case R.id.name:
                ToastUtil.showToast("抱歉，暂未开放此功能");
                break;
            case R.id.attention:
                ToastUtil.showToast("抱歉，暂未开放此功能");
                break;
            case R.id.chat:
                ToastUtil.showToast("抱歉，暂未开放此功能");

                break;
            case R.id.send_wish:
                sendWish();
                break;
            case R.id.about_me:
                queryAboutMe();
                break;
            case R.id.luohe:
                queryTaLuohe();
                break;
            case R.id.theme:   //问令:
                Intent taLuoheIntent = new Intent(this,HisThemeListActivity.class);
                taLuoheIntent.putExtra("userId",userId);
                startActivity(taLuoheIntent);
                break;
            case R.id.wenfeng:
                Intent taWenFengIntent = new Intent(this,HisWenFengListActivity.class);
                taWenFengIntent.putExtra("styleUserId",userId);
                startActivity(taWenFengIntent);
                break;
            case R.id.share:
                ToastUtil.showToast("抱歉，暂未开放此功能");
                break;
        }
    }

    private void queryTaLuohe() {
        Intent taLuoheIntent = new Intent(this,HisLuoheListActivity.class);
        taLuoheIntent.putExtra("userId",userId);
      startActivity(taLuoheIntent);
    }

    private void queryAboutMe() {
        Intent aboutMeIntent = new Intent(this, AboutMeActivity.class);
        aboutMeIntent.putExtra("uid", userId);
        startActivity(aboutMeIntent);
    }

    private void sendWish() {
        //// TODO: 2016/6/13
    }
}
