package com.luohe.android.luohe.message;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMContactsUI;
import com.luohe.android.luohe.R;

/**
 * Created by cegrano on 16/3/13.
 */
public class IMContactSample extends IMContactsUI {
    public IMContactSample(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public View getCustomTitle(Fragment fragment, Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_empty, new RelativeLayout(context), false);
        return view;
    }
}
