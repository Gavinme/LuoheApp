package com.luohe.android.luohe.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.utils.ViewMapping;
import com.luohe.android.luohe.utils.ViewMappingUtil;

/**
 * Created by Quan on 16/1/8. tabview
 * 对原有tabview做了一个抽象，实现了右上方小红点和数量提醒，并且提供了一些常用接口
 */
public class CommonTabView {
    private Context mContext;
    private View mView;

    private String mName;
    private int mIconResId;
    @ViewMapping(id = R.id.img_icon)
    private ImageView img_icon;

    @ViewMapping(id = R.id.tv_redtip_count)
    private TextView tv_redtip_count;
    @ViewMapping(id = R.id.img_red_tip)
    private ImageView img_red_tip;
    @ViewMapping(id = R.id.tv_tab)
    private TextView tv_tab;

    private Class mFragmentClazz;

    public CommonTabView(Context context) {
        this.mContext = context;
        onCreateView();
    }

    public CommonTabView(Context context, String name, int iconResId, Class clazz) {
        this(context);
        this.mName = name;
        this.mIconResId = iconResId;
        mFragmentClazz = clazz;
        onCreateView();
    }

    public String getName() {
        return this.mName;

    }

    public Class getFragmentClazz() {
        return this.mFragmentClazz;
    }

    private void onCreateView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.common_tab_view,
                null);
        mView.setTag(mFragmentClazz);
        ViewMappingUtil.mapView(this, mView);
        tv_tab.setText(mName);
        img_icon.setImageResource(mIconResId);
    }

    public View getView() {
        return this.mView;
    }

    /**
     * set the red tips count
     *
     * @param count
     */
    private void setTipCount(String count) {
        if (!TextUtils.isEmpty(count)) {
            tv_redtip_count.setText(count + "");
            tv_redtip_count.setVisibility(View.VISIBLE);
        } else {
            tv_redtip_count.setVisibility(View.GONE);
        }

    }

    /**
     * set red tip show or not
     *
     * @param show
     */
    private void setRedTip(boolean show) {
        img_red_tip.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 统一外部接口
     *
     * @param show
     */
    public void setRedDotVisibility(boolean show) {
        setRedTip(show);

    }

    /**
     * 显示带有数字提示的小红点
     *
     * @param count null or ""不显示 否则显示红点和个数
     */
    public void setRedDotCount(String count) {
        setTipCount(count);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mView.setOnClickListener(onClickListener);
    }
}
