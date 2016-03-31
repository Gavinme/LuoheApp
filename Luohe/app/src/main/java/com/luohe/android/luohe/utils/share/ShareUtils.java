package com.luohe.android.luohe.utils.share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.common.MyApplication;
import com.luohe.android.luohe.utils.ToastUtil;
import com.luohe.android.luohe.utils.share.onekeyshare.OnekeyShare;

import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 分享
 */

public class ShareUtils {
    /**
     * 分享到 好友
     */
    public static final int FRIENDS = 1;
    /**
     * 分享到 好友圈
     */
    public static final int FEEDS = 0;
    /**
     * 分享到朋友圈的ID *
     */
    public static final int MOMENTS = 2;    //朋友圈
    /**
     * 分享到微信好友的ID *
     */
    public static final int WECHAT = 3;        //微信好友
    /**
     * 分享到QQ空间的ID *
     */
    public static final int QZONE = 4;    //qq空间
    /**
     * 分享到QQ的ID *
     */
    public static final int TECENT = 5;        //QQ
    /**
     * 分享到新浪微博的ID *
     */
    public static final int WEIBO = 6;        //新浪微博
    /**
     * 调用系统分享的ID *
     */
    public static final int SHARE_ALL = -1;    //系统分享
    // sdcard中的图片名称
    private static final String FILE_NAME = "share_pic.png";
    private static final String COMMON_TITLE = "分享";
    public static String TEST_IMAGE = null;
    private static String SHARE_TITLE = ""; //分享标题
    private static String CLICKED_URL = "";    //分享后点击的链接
    private static String TITLE_URL = "";    //标题可点时的链接

    /**
     * 不需要修改URL的分享
     *
     * @param share_id
     * @param content
     * @param imgPath
     * @param listener
     */
    public static void showShare(int share_id, String content, String imgPath, ShareResultListener listener) {
        switch (share_id) {
            case FRIENDS:
                ToastUtil.showToast("分享到best好友,需要修改");
                break;
            case FEEDS:
                ToastUtil.showToast("分享到best圈,需要修改");
                break;
            case MOMENTS:
                share2moments(content, imgPath, listener);
                break;
            case WECHAT:
                share2wechat(content, imgPath, listener);
                break;
            case QZONE:
                share2Qzone(content, imgPath, listener);
                break;
            case TECENT:
                share2tecent(content, imgPath, listener);
                break;
            case WEIBO:
                share2weibo(content, imgPath, listener);
                break;
            case SHARE_ALL:
                share2all(MyApplication.getContext(), content);
                break;
            default:
                ToastUtil.showToast("当前id无对应分享平台");
                break;
        }
    }

    /**
     * 标题，内容，URL，logo都可以自定义
     *
     * @param share_id
     * @param content
     * @param imgPath
     * @param url
     * @param title
     * @param listener
     */
    public static void showShare(int share_id, String content, String imgPath, String url, String title, ShareResultListener listener) {
        if (!TextUtils.isEmpty(url))
            CLICKED_URL = url;
        if (!TextUtils.isEmpty(title))
            SHARE_TITLE = title;
        if (TextUtils.isEmpty(content))
            content = COMMON_TITLE;
        showShare(share_id, content, imgPath, listener);
    }

    /**
     * 一键分享到QQ空间
     *
     * @param context
     * @param content
     * @param imgPath
     * @param title
     * @param click_url
     */
    public static void share2qzone(Context context, String content, String imgPath, String title, String click_url, ShareResultListener listener) {
        if (!TextUtils.isEmpty(title)) {
            SHARE_TITLE = title;
        }
        if (!TextUtils.isEmpty(click_url)) {
            CLICKED_URL = click_url;
        }
        if (TextUtils.isEmpty(content)) {
            content = COMMON_TITLE;
        }
        onekeyShare(context, QZone.NAME, content, imgPath, click_url, listener);
    }

    /**
     * 一键分享到新浪微博
     *
     * @param context
     * @param content
     * @param imgPath
     * @param title
     * @param click_url
     */
    public static void share2weibo(Context context, String content, String imgPath, String title, String click_url, ShareResultListener listener) {
        if (!TextUtils.isEmpty(title)) {
            SHARE_TITLE = title;
        }
        if (!TextUtils.isEmpty(click_url)) {
            CLICKED_URL = click_url;
        }
        if (TextUtils.isEmpty(content)) {
            content = COMMON_TITLE;
        }
        onekeyShare(context, SinaWeibo.NAME, content, imgPath, click_url, listener);
    }

    public static void share2weibo(String content, String imgPath, String title, String click_url, final ShareResultListener listener) {
        if (!TextUtils.isEmpty(title)) {
            SHARE_TITLE = title;
        }
        if (!TextUtils.isEmpty(click_url)) {
            CLICKED_URL = click_url;
        }
        if (TextUtils.isEmpty(content)) {
            content = COMMON_TITLE;
        }
        share2weibo(content, imgPath, listener);
    }

    public static void share2Qzone(String content, String imgPath, String title, String click_url, final ShareResultListener listener) {
        if (!TextUtils.isEmpty(title)) {
            SHARE_TITLE = title;
        }
        if (!TextUtils.isEmpty(click_url)) {
            CLICKED_URL = click_url;
        }
        if (TextUtils.isEmpty(content)) {
            content = COMMON_TITLE;
        }
        share2Qzone(content, imgPath, listener);
    }

    public static void share2moments(String content, String imgPath, String title, String click_url, final ShareResultListener listener) {
        if (!TextUtils.isEmpty(title)) {
            SHARE_TITLE = title;
        }
        if (!TextUtils.isEmpty(click_url)) {
            CLICKED_URL = click_url;
        }
        if (TextUtils.isEmpty(content)) {
            content = COMMON_TITLE;
        }
        share2moments(content, imgPath, listener);
    }

    /**
     * 微信朋友圈分享
     *
     * @param content
     * @param imgPath
     */
    public static void share2moments(String content, String imgPath, final ShareResultListener listener) {
        ShareParams sp = new ShareParams();
        sp.setShareType(WechatMoments.SHARE_WEBPAGE);
        sp.setUrl(CLICKED_URL);
        sp.setTitle(SHARE_TITLE);
        sp.setTitleUrl(TITLE_URL);
        sp.setText(content);
        if (TextUtils.isEmpty(imgPath)) {
            sp.setImagePath(TEST_IMAGE);
        } else {
            sp.setImageUrl(imgPath);
        }

        Platform moments = ShareSDK.getPlatform(WechatMoments.NAME);
        moments.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if (null != listener)
                    listener.onSuccess();
                ToastUtil.showToast("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if (null != listener) {
                    listener.onFailure(i);
                }
                ToastUtil.showToast("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                if (null != listener) {
                    listener.onFailure(i);
                }
                ToastUtil.showToast("取消分享");
            }
        }); // 设置分享事件回调
        // 执行图文分享
        moments.share(sp);
    }

    public static void share2wechat(String title, String content, String url, final ShareResultListener listener) {
        SHARE_TITLE = title;
        CLICKED_URL = url;
        share2wechat(content, "", null);
    }

    /**
     * 微信好友分享
     *
     * @param content
     * @param imgPath
     */
    public static void share2wechat(String content, String imgPath, final ShareResultListener listener) {
        ShareParams sp = new ShareParams();
        sp.setShareType(Wechat.SHARE_WEBPAGE);
        sp.setUrl(CLICKED_URL);
        sp.setTitle(SHARE_TITLE);
        sp.setTitleUrl(TITLE_URL);
        sp.setText(content);
        if (TextUtils.isEmpty(imgPath)) {
            sp.setImagePath(TEST_IMAGE);
        } else {
            sp.setImageUrl(imgPath);
        }

        Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
        wechat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if (null != listener)
                    listener.onSuccess();
                ToastUtil.showToast("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if (null != listener) {
                    listener.onFailure(i);
                }
                ToastUtil.showToast("分享失败");
                LogUtils.e("ShareUtil", i + "--- 分享失败 ");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                if (null != listener) {
                    listener.onFailure(i);
                }
                ToastUtil.showToast("取消分享");
            }
        }); // 设置分享事件回调
        // 执行图文分享
        wechat.share(sp);
    }

    public static void share2tecent(String title, String content, String url, final ShareResultListener listener) {
        SHARE_TITLE = title;
        CLICKED_URL = url;
        share2tecent(content, "", null);
    }

    /**
     * qq分享
     *
     * @param content
     * @param imgPath
     * @param listener
     */
    public static void share2tecent(String content, String imgPath, final ShareResultListener listener) {
        ShareParams sp = new ShareParams();
        sp.setTitle(SHARE_TITLE);
        sp.setTitleUrl(CLICKED_URL);
        sp.setSiteUrl(TITLE_URL);
        sp.setText(content);
        if (TextUtils.isEmpty(imgPath)) {
            sp.setImagePath(TEST_IMAGE);
        } else {
            sp.setImageUrl(imgPath);
        }

        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if (null != listener)
                    listener.onSuccess();
                ToastUtil.showToast("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if (null != listener)
                    listener.onFailure(i);
                ToastUtil.showToast("分享失败");
                LogUtils.w("ShareUtil", i + "--- 分享失败 ");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                if (null != listener) {
                    listener.onFailure(i);
                }
                ToastUtil.showToast("取消分享");
            }
        }); // 设置分享事件回调
        // 执行图文分享
        qq.share(sp);
    }

    /**
     * qq分享
     *
     * @param content
     * @param imgPath
     * @param listener
     */
    public static void share2Qzone(String content, String imgPath, final ShareResultListener listener) {
        ShareParams sp = new ShareParams();
        sp.setTitle(SHARE_TITLE);
        sp.setTitleUrl(CLICKED_URL); // 标题的超链接
        sp.setText(content);
        sp.setImagePath(TEST_IMAGE);
        if (TextUtils.isEmpty(imgPath)) {
            sp.setImagePath(TEST_IMAGE);
        } else {
            sp.setImageUrl(imgPath);
        }
        sp.setSite(SHARE_TITLE);
        sp.setSiteUrl(CLICKED_URL);
        Platform qzone = ShareSDK.getPlatform(QZone.NAME);
        qzone.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if (null != listener)
                    listener.onSuccess();
                ToastUtil.showToast("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if (null != listener)
                    listener.onFailure(i);
                ToastUtil.showToast("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                if (null != listener)
                    listener.onFailure(i);
                ToastUtil.showToast("取消分享");
            }
        }); // 设置分享事件回调
        // 执行图文分享
        qzone.share(sp);
    }

    public static void share2weibo(String title, String content, String url, final ShareResultListener listener) {
        SHARE_TITLE = title;
        CLICKED_URL = url;
        share2weibo(content, null, null);
    }

    /**
     * 微博分享
     */
    public static void share2weibo(String content, String imgPath, final ShareResultListener listener) {
        ShareParams sp = new ShareParams();
        sp.setText(SHARE_TITLE + CLICKED_URL);
        if (TextUtils.isEmpty(imgPath)) {
            sp.setImagePath(TEST_IMAGE);
        } else {
            sp.setImageUrl(imgPath);
        }

        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if (null != listener)
                    listener.onSuccess();
                ToastUtil.showToast("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if (null != listener)
                    listener.onFailure(i);
                ToastUtil.showToast("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                if (null != listener)
                    listener.onFailure(i);
                ToastUtil.showToast("取消分享");
            }
        }); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp);
    }

    /**
     * 系统分享
     *
     * @param context
     * @param content
     */
    public static void share2all(Context context, String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, "分享"));
    }

    /**
     * 是否安装某个应用
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isInstall(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }


    public static void onekeyShare(Context context, String platform, String content, String imgUrl, String share_url, final ShareResultListener listener) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(SHARE_TITLE);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(TITLE_URL);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(content);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        if (TextUtils.isEmpty(imgUrl)) {
            oks.setImagePath(TEST_IMAGE);//确保SDcard下面存在此张图片
        } else {
            oks.setImageUrl(imgUrl);
        }
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(share_url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(COMMON_TITLE);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(TITLE_URL);
        oks.setSilent(false);
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                if (null != listener)
                    listener.onSuccess();
                LogUtils.w("ShareUtils", "onComplete----------<");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if (null != listener)
                    listener.onFailure(i);
                LogUtils.w("ShareUtils", "onError----------<");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                if (null != listener)
                    listener.onFailure(i);
                LogUtils.w("ShareUtils", "onCancel----------<");
            }
        });
        oks.setPlatform(platform);
        // 启动分享GUI
        oks.show(context);
    }

    public interface ShareResultListener {
        void onSuccess();

        void onFailure(int errorCode);
    }
}
