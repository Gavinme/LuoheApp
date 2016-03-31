package com.luohe.android.luohe.utils.share;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.utils.AppInfoUtil;
import com.luohe.android.luohe.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cegrano on 15/8/18.
 */
public class ShareHelper {
    private static RelativeLayout sLl_popup;
    private static PopupWindow sPop;
    /**
     * 预备 PopupWindow
     *
     * @param context
     * @param resultListener
     */
    private static int defTargetPos = 0;
    private static int defLevelPos = 0;
    private static int defPartsPos = 0;

    /**
     * 预备 PopupWindow
     *
     * @param context
     * @param items
     * @param listener
     */
    public static void preShareWindow(final Context context, List<ShareItem> items, final ShareClickListener listener) {
        if (null == sPop)
            sPop = new PopupWindow(context);

        View view = View.inflate(context, R.layout.layout_share_pop, null);
        sLl_popup = (RelativeLayout) view.findViewById(R.id.ll_share);
        sPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        sPop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        sPop.setBackgroundDrawable(new BitmapDrawable());
        sPop.setFocusable(true);
        sPop.setOutsideTouchable(true);
        sPop.setContentView(view);
        sLl_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareHelper.hidePopWindow(context);
            }
        });
        GridView gridView = (GridView) view.findViewById(R.id.gdv_share);
        if (ListUtils.isEmpty(items))
            items = new ArrayList<>();
        items.add(new ShareItem(R.drawable.ic_share_moments, "朋友圈"));
        items.add(new ShareItem(R.drawable.ic_share_wechat, "微信"));
        items.add(new ShareItem(R.drawable.ic_share_qzone, "QZone"));
        items.add(new ShareItem(R.drawable.ic_share_qq, "QQ"));
        items.add(new ShareItem(R.drawable.ic_share_weibo, "微博"));
        final ShareGridViewAdapter adapter = new ShareGridViewAdapter(context, items);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemClick(position, adapter.getItem(position));
            }
        });
    }


    /**
     * 底部显示 PopupWindow
     *
     * @param context 上下文
     * @param view    View anchor
     */
    public static void showPopWindow(Context context, View view) {
        if (null != sPop) {
            sLl_popup.startAnimation(AnimationUtils.loadAnimation(context, R.anim.choose_login_bottom_in));
            sPop.showAtLocation(view, Gravity.BOTTOM, 0, AppInfoUtil.getNavigationBarHeight(context));
        }
    }

    /**
     * 隐藏 PopupWindow
     *
     * @param context 上下文
     */
    public static void hidePopWindow(Context context) {
        if (null != sPop && sPop.isShowing()) {
            sPop.dismiss();
            sLl_popup.clearAnimation();
        }
    }

    /**
     * PopupWindow置为空
     * <p/>
     * onDestroy() 调用
     */
    public static void resetPop() {
        if (null != sPop) {
            if (sPop.isShowing())
                sPop.dismiss();
            if (null != sLl_popup) {
                sLl_popup.clearAnimation();
                sLl_popup = null;
            }
            sPop = null;
        }
    }

    /**
     * 隐藏 PopupWindow
     */
    public static void hidePopNoAnimi() {
        if (null != sPop && sPop.isShowing()) {
            sPop.dismiss();
        }
    }

    public interface ShareClickListener {

        void onItemClick(int position, Object object);
    }

    public interface OnWheelResultListener {
        void onSuccess(int target, int level, int parts);
    }
}
