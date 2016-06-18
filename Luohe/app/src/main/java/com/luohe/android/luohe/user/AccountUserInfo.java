package com.luohe.android.luohe.user;

import android.text.TextUtils;

import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.utils.SpUtils;

import java.io.Serializable;

/**
 * Created by GanQuan on 16/4/25.
 */
public class AccountUserInfo implements Serializable {
    private UserCommonInfo mUserCommonInfo;
    private UserLoginInfo mUserLoginInfo;

    public AccountUserInfo() {
        mUserCommonInfo = new UserCommonInfo();
        mUserLoginInfo = new UserLoginInfo();
    }

    /**
     * set personal information
     *
     * @param info
     */
    public void setComUserInfo(UserCommonInfo info) {
        this.mUserCommonInfo = info;
    }

    public UserCommonInfo getComUserInfo() {
        return this.mUserCommonInfo;
    }

    public String getToken() {
        return mUserLoginInfo.token;
    }

    public void setToken(String token) {
        mUserLoginInfo.token = token;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(mUserLoginInfo.token);
    }

    public void logOut() {
        mUserLoginInfo.token = null;
        clearLogInfoInSdCard();
    }

    private void clearLogInfoInSdCard() {
        SpUtils.getSpUtilsInstance().setString(ConstantsUtil.access_token, "");

    }

    public String getTrueName() {
        return mUserLoginInfo.trueName;
    }

    public void setTrueName(String name) {
        this.mUserLoginInfo.trueName = name;
    }

    public int getUid() {
        return mUserLoginInfo.id;
    }

    public void setUid(int uid) {
        mUserLoginInfo.id = uid;
    }

    public String getAccount() {
        return mUserLoginInfo.account;
    }

    public void setAccount(String account) {
        mUserLoginInfo.account = account;
    }

    private void updateLogin(int uid, String token, String account) {
        mUserLoginInfo.id = uid;
        mUserLoginInfo.token = token;
        mUserLoginInfo.account = account;
    }

    /**
     * @param info
     */
    public void updateLogin(UserLoginInfo info) {
        updateLogin(info.id, info.token, info.account);
    }

}
