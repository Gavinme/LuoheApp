package com.luohe.android.luohe.message;

import android.support.v4.app.Fragment;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;

/**
 * Created by cegrano on 16/3/13.
 * 继承自IMConversationListUI的自定义类
 */
public class IMConversationListSample extends IMConversationListUI {
    public IMConversationListSample(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public boolean needHideTitleView(Fragment fragment) {
        return true;
    }

    @Override
    public boolean enableSearchConversations(Fragment fragment) {
        return false;
    }
}
