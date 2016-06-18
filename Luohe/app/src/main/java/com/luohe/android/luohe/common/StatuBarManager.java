package com.luohe.android.luohe.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.luohe.android.luohe.R;

/**
 * Created by GanQuan on 16/3/5.
 */
public class StatuBarManager {


    public static void setTintStyle(Activity activity) {
        if (hasKitKat() && !hasLollipop()) {
            //透明状态栏
            (activity).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (hasLollipop()) {
            Window window = (activity).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.app_main_blue));
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void setImmersive(TitleBar titleBar, View parent) {
        if (hasKitKat() && !hasLollipop()) {
            titleBar.setImmersive(true);

            parent.setFitsSystemWindows(false);
        } else if (hasLollipop()) {
            titleBar.setImmersive(false);
            parent.setFitsSystemWindows(true);
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void setImmersive(TitleBar titleBar,View parent,boolean flag){
        if (flag){
            titleBar.setImmersive(false);
            parent.setFitsSystemWindows(true);
        }
    }

    private static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    private static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
