package com.luohe.android.luohe.common;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.wxlib.util.SysUtil;
import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.net.data.RequestManager;
import com.luohe.android.luohe.utils.LocalDisplay;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.openim.OpenIMAgent;

import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by GanQuan on 16/2/26.
 */
public class MyApplication extends Application {
	private static final String APP_KEY = "23325655";
	private static Context sContext;

	public static Context getContext() {
		return sContext;
	}

	// 初始化ImageLoader
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024)).diskCacheSize(10 * 1024 * 1024)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		ImageLoader.getInstance().init(config);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sContext = getApplicationContext();
		RequestManager.init(this);// init volley request queue
		initImageLoader(getApplicationContext());
		LogUtils.setDebug(true);
		ButterKnife.setDebug(ConstantsUtil.DEBUG);

		LocalDisplay.init(this);// 初始化local屏幕参数
		// todo
		// Application.onCreate中，首先执行这部分代码，以下代码固定在此处，不要改动，这里return是为了退出Application.onCreate！！！
		if (mustRunFirstInsideApplicationOnCreate()) {
			// todo 如果在":TCMSSevice"进程中，无需进行openIM和app业务的初始化，以节省内存
			return;
		}

		// 初始化云旺SDK
		OpenIMAgent im = OpenIMAgent.getInstance(this);
		im.init();
		ShareSDK.initSDK(this);
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	private boolean mustRunFirstInsideApplicationOnCreate() {
		// 必须的初始化
		SysUtil.setApplication(this);
		sContext = getApplicationContext();
		return SysUtil.isTCMSServiceProcess(sContext);
	}
}
