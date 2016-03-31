package com.luohe.android.luohe.find;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;

/**
 * Created by GanQuan on 16/3/20.
 */
public class FindItemView extends FrameLayout {
    TextView leftTextview;
    TextView tips_count;
    ImageView img_right;
    ImageView right_tip;

    public FindItemView(Context context) {
        super(context);
        initViews();
    }

    public FindItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public FindItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_include_itemfind, this);
        leftTextview = (TextView) view.findViewById(R.id.left_text);
        tips_count = (TextView) view.findViewById(R.id.tip_count);
        img_right = (ImageView) view.findViewById(R.id.img_right);
        right_tip = (ImageView) view.findViewById(R.id.right_tip);
        img_right.setVisibility(GONE);
        right_tip.setVisibility(GONE);
    }

    public void setTipCount(int count) {
        tips_count.setText(count + "");
        tips_count.setVisibility(VISIBLE);
    }

    public void setTipcountVisiable(int flag) {
        tips_count.setVisibility(flag);
    }

    /**
     * @param drawLeftId drawpaddingleft
     * @param str        text
     */
    public void setLeftTextview(int drawLeftId, String str) {
        this.leftTextview.setText(str);
        this.leftTextview.setCompoundDrawablesWithIntrinsicBounds(drawLeftId, 0, 0, 0);
    }

    public void setImg_right(int drawId) {
        this.img_right.setBackgroundResource(drawId);

    }

    public void setImgRigthVisiable(int flag) {
        this.img_right.setVisibility(flag);
    }

    public void setRightTipVisiable(int flag) {
        this.img_right.setVisibility(flag);
    }


}
