package com.luohe.android.luohe.mine;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.user.ModifyPasswordActivity;
import com.luohe.android.luohe.user.UserCommonInfo;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.ToastUtil;

import butterknife.Bind;

public class SecurityActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.security_luohe_account)
    SettingItemView securityLuoheAccount;
    @Bind(R.id.security_luohe_password)
    SettingItemView securityLuohePassword;
    @Bind(R.id.security_chat)
    SettingItemView securityChat;
    @Bind(R.id.security_luohe_blacklist)
    SettingItemView securityLuoheBlacklist;


    @Override
    protected void init(Bundle savedInstanceState) {

        String luoheAccount =UserInfoUtil.getInstance().getUserInfo().getAccount();
        getTitlebar().setTitle("安全与隐私");
        getTitlebar().setDefauleBackBtn();
        if (!UserInfoUtil.getInstance().IsLogin()) {
            getLoadingHelper().showNetworkError();
            return;
        }
        initViews(luoheAccount);

    }

    private void initViews(String luoheAccount) {
        securityLuoheAccount.setLeftTextview(0, "落和账号");
        securityLuoheAccount.setRightBowVisiable(View.INVISIBLE);
        securityLuoheAccount.setRightText(luoheAccount);
        securityLuohePassword.setLeftTextview(0, "落和密码");
        securityLuohePassword.setOnClickListener(this);
        securityLuohePassword.setRightText("********");
        securityChat.setLeftTextview(0, "聊天（仅好友聊天）");
        securityLuoheBlacklist.setLeftTextview(0, "落和黑名单");
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_security;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.security_luohe_password:
                startActivity(new Intent(this, ModifyPasswordActivity.class));

                break;
        }
    }
}
