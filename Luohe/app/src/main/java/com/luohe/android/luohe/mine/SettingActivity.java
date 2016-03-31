package com.luohe.android.luohe.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;

import butterknife.Bind;

/**
 * Created by GanQuan on 16/3/20.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.luohe_account)
    SettingItemView luoheAccount;
    @Bind(R.id.nick_name)
    SettingItemView nickName;
    @Bind(R.id.avatar)
    SettingItemView avatar;
    @Bind(R.id.real_name)
    SettingItemView realName;
    @Bind(R.id.sex)
    SettingItemView sex;
    @Bind(R.id.birthday)
    SettingItemView birthday;
    @Bind(R.id.address)
    SettingItemView address;
    @Bind(R.id.person_introduce)
    SettingItemView personIntroduce;
    @Bind(R.id.person_style)
    SettingItemView personStyle;

    @Override
    protected void init(Bundle savedInstanceState) {
        initViews();
        getTitlebar().setTitle("设置");
        getTitlebar().setDefauleBackBtn();
    }

    private void initViews() {
        luoheAccount.setLeftTextview(0, "落和令帐号");
        luoheAccount.setOnClickListener(this);
        nickName.setLeftTextview(0, "落和令昵称");
        nickName.setOnClickListener(this);
        avatar.setLeftTextview(0, "头像");
        avatar.setOnClickListener(this);
        realName.setLeftTextview(0, "真实姓名");
        realName.setOnClickListener(this);
        sex.setLeftTextview(0, "性别");
        sex.setOnClickListener(this);
        birthday.setLeftTextview(0, "生日");
        birthday.setOnClickListener(this);
        address.setLeftTextview(0, "所在地");
        address.setOnClickListener(this);
        personIntroduce.setLeftTextview(0, "个人介绍");
        personIntroduce.setOnClickListener(this);
        personStyle.setLeftTextview(0, "创者风格");
        personStyle.setOnClickListener(this);

    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.luohe_account:
                startActivity(new Intent(this, NickNameSetActivity.class));
                break;
            case R.id.nick_name:
                break;
            case R.id.avatar:
                startActivity(new Intent(this, AvatarSetActivity.class));
                break;
            case R.id.real_name:
                break;
            case R.id.sex:
                break;
            case R.id.birthday:
                break;
            case R.id.address:
                break;
            case R.id.person_introduce:
                break;
            case R.id.person_style:
                break;
        }
    }
}
