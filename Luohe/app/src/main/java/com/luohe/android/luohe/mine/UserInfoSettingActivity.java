package com.luohe.android.luohe.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.user.UserCommonInfo;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.ToastUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: GanQuan Date: 16/5/28 Email:ganquan3640@gmail.com
 */

public class UserInfoSettingActivity extends SimpleInputActivity {
    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    protected String onSetTitle() {
        return getIntent().getStringExtra(ConstantsUtil.name);
    }

    @Override
    protected void onSaveAction(Context context) {
        UserRequest userRequest = new UserRequest();
        if (!isStrEmpty(edit_input_key.getText().toString())) {
            String saveStr = edit_input_key.getText().toString();
            handlerId(saveStr, userRequest);
            ApiLoader.getApiService().updateUserInfo(ParmarsUtils.getParamas(userRequest)).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonSubscriber<Result>(this) {
                @Override
                public void onSuccess(Result result) {
                    if (result.isHasReturnValidCode()) {
                        ToastUtil.showToast("设置成功");
                        setUser(UserInfoUtil.getInstance().getUserInfo().getComUserInfo(), edit_input_key.getText().toString());
                        saveSuc(edit_input_key.getText().toString());
                        finish();
                    } else {
                        ToastUtil.showToast("设置失败");
                    }
                }


                @Override
                public void onError(Throwable e) {
                    super.onError(e);
                    ToastUtil.showToast("设置失败");
                }
            });

        } else {
            ToastUtil.showToast("请输入内容");
        }

    }

    private void saveSuc(String s) {
        switch (getIntent().getIntExtra(ConstantsUtil.id, -1)) {
            case 1:
                EventBusControl.post(s, "set_true_name");
                break;
            case 2:
                EventBusControl.post(s, "set_account_desc");
                break;
            case 3:
                EventBusControl.post(s, "set_nikename");
                break;
        }

    }

    private void setUser(UserCommonInfo comUserInfo, String s) {
        int key = getIntent().getIntExtra(ConstantsUtil.id, -1);
        switch (key) {
            case 1:
                UserInfoUtil.getInstance().getUserInfo().setTrueName(s);
                break;
            case 2:
                comUserInfo.accountDesc = s;
                break;
            case 3:
                comUserInfo.nickName = s;
                break;
        }
    }

    private void handlerId(String saveStr, UserRequest userRequest) {
        int key = getIntent().getIntExtra(ConstantsUtil.id, -1);
        switch (key) {
            case 1:
                userRequest.trueName = saveStr;
                break;
            case 2:
                userRequest.accountDesc = saveStr;
                break;
            case 3:
                userRequest.nickname = saveStr;
                break;
        }

    }

    public static Intent getStartIntent(Context context, int id, String name) {
        Intent it = new Intent(context, UserInfoSettingActivity.class);
        it.putExtra(ConstantsUtil.id, id);
        it.putExtra(ConstantsUtil.name, name);
        return it;
    }
}
