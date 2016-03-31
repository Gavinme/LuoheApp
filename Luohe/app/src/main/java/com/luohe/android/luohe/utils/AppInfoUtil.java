package com.luohe.android.luohe.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.common.MyApplication;

import java.lang.reflect.Method;

/**
 * 获得应用信息
 */
public class AppInfoUtil {

    private static final String TAG = AppInfoUtil.class.getSimpleName();

    /**
     * 判断 新版本 是否可用
     *
     * @param context     上下文对象
     * @param versionCode 新的版本号
     * @return
     */
    public static boolean isNewVersionAvailable(Context context, long versionCode) {
        long code = getVersionCode(context);
        return (versionCode > code);
    }

    public static String getVersionName() {
        try {
            PackageInfo pi = getPackageInfo(MyApplication.getContext());
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 返回当前包名
     *
     * @return
     */
    private static String getCurrentPkgName(Context context) {
        try {
            PackageInfo pi = getPackageInfo(context);
            return pi.packageName;
        } catch (Exception e) {
            LogUtils.e("AppInfo", "Exception " + e);
        }
        return "";
    }

    public static long getVersionCode(Context context) {
        try {
            return getPackageInfo(context).versionCode;
        } catch (Exception e) {
            LogUtils.e("AppInfo", "判断版本号获取错误::" + e.toString());
        }
        return -1;
    }

    public static PackageInfo getPackageInfo(Context context) throws Exception {
        PackageManager pm = context.getPackageManager();
        return pm.getPackageInfo(context.getPackageName(), 0);
    }

    /**
     * 获得manifase中得metadata数据
     *
     * @param key 主键
     * @return 内容
     * @throws PackageManager.NameNotFoundException
     */
    public static String getMetaDate(String key) throws PackageManager.NameNotFoundException {
        ApplicationInfo appInfo = MyApplication.getContext().getPackageManager()
                .getApplicationInfo(MyApplication.getContext().getPackageName(),
                        PackageManager.GET_META_DATA);
        return appInfo.metaData.getString(key).trim();

    }

    /**
     * 获得mac地址
     */
    public static String getMacAddress(Context context) {
        try {
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            return wm.getConnectionInfo().getMacAddress();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    /**
     * 获得IMEI
     */
    public static String getDeviceId() {
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            return TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
        } catch (Exception e) {
        }
        return "0";
    }


    /**
     * 获取手机及系统等信息
     *
     * @return
     */
    public static String getHandSetInfo() {
        String handSetInfo =
                "手机型号:" + android.os.Build.MODEL +
                        ",SDK版本:" + android.os.Build.VERSION.SDK +
                        ",系统版本:" + android.os.Build.VERSION.RELEASE +
                        ",软件版本:" + getAppVersionName(MyApplication.getContext());
        return handSetInfo;
    }

    /**
     * 获取当前版本号
     */
    private static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("cn.testgethandsetinfo", 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }


    public static int getNaviHeight() {
        int height;
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
        boolean isOnePlus = android.os.Build.MODEL.endsWith("A0001");
        if (hasBackKey && hasHomeKey && !isOnePlus) {
            // 没有虚拟按键
//            LogUtils.e("AppInfo","----当前无虚拟键---" + AppInfoUtil.getHandSetInfo());
            height = 0;
        } else {
            // 有虚拟按键：99%可能。
            height = getNavigationBarHeight(MyApplication.getContext());
        }
        return height;
    }

    /**
     * 获取NavigationBar的高度：
     *
     * @param context 上下文
     * @return 虚拟键高度px值
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
            LogUtils.e("AppInfo", "navigation_bar_height" + navigationBarHeight);
        }
        return navigationBarHeight;
    }

    //	获取是否存在NavigationBar：
    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;

    }
}
