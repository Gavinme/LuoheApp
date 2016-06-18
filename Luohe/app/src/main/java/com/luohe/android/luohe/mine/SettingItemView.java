package com.luohe.android.luohe.mine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.luohe.android.luohe.find.FindItemView;

/**
 * Created by GanQuan on 16/3/20.
 */
public class SettingItemView extends FindItemView {
    public SettingItemView(Context context) {
        super(context);
        setStyle();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStyle();
    }


    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setStyle();
    }

    private void setStyle() {
        this.setLeftTextview(0, "");//remove drawleft
        this.setRightTipVisiable(GONE);
        this.setTipcountVisiable(View.GONE);
    }

}
