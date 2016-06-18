package com.luohe.android.luohe.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by GanQuan on 16/4/12.
 */
public abstract class BaseViewWrap extends FrameLayout {

    public BaseViewWrap(Context context) {
        super(context);
        onCreate(context);
    }


    public BaseViewWrap(Context context, AttributeSet attrs) {
        super(context, attrs);
        onCreate(context);
    }

    public BaseViewWrap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onCreate(context);

    }

    protected void onCreate(Context context) {
        View view = LayoutInflater.from(context).inflate(onBindLayoutId(), this);
        onViewCreated(view);
    }


    protected abstract void onViewCreated(View mView);

    protected abstract int onBindLayoutId();


}
