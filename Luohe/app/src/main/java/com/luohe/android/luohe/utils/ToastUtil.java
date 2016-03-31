package com.luohe.android.luohe.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.appmonitor.tiptool.DensityUtil;
import com.luohe.android.luohe.common.MyApplication;

public class ToastUtil {
    public static final short TOAST_NORMAL = 1;
    public static final short TOAST_SINGLE = 2;

    private static ToastUtil INSTANCE;
    private Context mContext = MyApplication.getContext();
    private Toast mSingleToast;
    private Toast mToast;
    private final Handler mCommonToastHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mToast = Toast.makeText(mContext, msg.obj.toString(), msg.arg1 == 1 ? Toast.LENGTH_LONG
                        : Toast.LENGTH_SHORT);
                mToast.show();
            } else if (mToast != null) {
                mToast.cancel();
            }
        }
    };
    private TextView mToastTextView;
    private final Handler mSingleToastHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (mToast != null)
                    mToast.cancel();

                if (mSingleToast == null || Integer.parseInt(mSingleToast.getView().getTag().toString()) != msg.arg2) {
                    mSingleToast = Toast.makeText(mContext, msg.obj.toString(), msg.arg1 == 1 ? Toast.LENGTH_LONG
                            : Toast.LENGTH_SHORT);
                    ViewGroup toastView = (ViewGroup) mSingleToast.getView();
                    if (msg.arg2 == 1) {
                        toastView.removeViewAt(0);
                        mToastTextView = new TextView(mContext);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DensityUtil.px2dip(mContext,
                                230), LinearLayout.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(2, 2, 2, 2);

                        mToastTextView.setLayoutParams(lp);
                        mToastTextView.setText(msg.obj.toString());
                        mToastTextView.setSingleLine();
                        toastView.addView(mToastTextView);
                    } else {
                        mToastTextView = (TextView) toastView.getChildAt(0);
                    }
                    toastView.setTag(msg.arg2);
                } else {
                    mToastTextView.setText(msg.obj.toString());
                    mSingleToast.setDuration(msg.arg1 == 1 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
                }

                mSingleToast.show();
            } else if (mSingleToast != null) {
                mSingleToast.cancel();
            }
        }
    };

    public synchronized static ToastUtil getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ToastUtil();

        return INSTANCE;
    }

    public static void showToast(String text) {
        getInstance().makeToast(text);
    }

    public static void showToast(int resId) {
        getInstance().makeToast(resId);
    }

    /**
     * @param text         message to be toasted.
     * @param isLong       is show long.
     * @param isFixedWidth if the toast has a fixed Width.
     */
    public void makeToast(String text, boolean isLong, boolean isFixedWidth) {
        Message msg = mSingleToastHandler.obtainMessage(1, isLong ? 1 : 0, isFixedWidth ? 1 : 0, text);
        mSingleToastHandler.sendMessage(msg);
    }

    public void makeToast(String text, boolean isLong) {
        Message msg = mCommonToastHandler.obtainMessage(1, isLong ? 1 : 0, 0, text);
        mCommonToastHandler.sendMessage(msg);
    }

    // 用这个避免toast过多，一个个弹出，占位太久
    public void makeToast(String text) {
        text = ((text == null) ? "null" : text);
        makeToast(text, false, false);
    }

    public void makeToast(int resId) {
        if (mContext == null)
            return;
        String text = mContext.getString(resId);
        makeToast(text);
    }

    public void cancelToast(int toastType) {
        switch (toastType) {
            case TOAST_NORMAL:
                mCommonToastHandler.sendEmptyMessage(2);
                break;
            case TOAST_SINGLE:
                mSingleToastHandler.sendEmptyMessage(2);
                break;
            default:
                mCommonToastHandler.sendEmptyMessage(2);
                mSingleToastHandler.sendEmptyMessage(2);
        }
    }

}
