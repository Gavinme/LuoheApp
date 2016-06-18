package com.luohe.android.luohe.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabWidget;

import com.androidplus.ui.ToastManager;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.luohe.HowToPlayActivity;
import com.luohe.android.luohe.user.LoginHelper;
import com.luohe.android.luohe.utils.share.ShareHelper;
import com.luohe.android.luohe.utils.share.ShareItem;
import com.luohe.android.luohe.utils.share.ShareUtils;
import com.luohe.android.luohe.widget.CommonTabView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by GanQuan on 16/2/19.
 */

public class MainTabActivity extends AppCompatActivity {

	private static final String APP_KEY = "23325655";
	final String TAG = MainTabActivity.class.getSimpleName();
	@Bind(R.id.tab_widget)
	TabWidget mTabWidget;
	private Context mContext;
	private FragmentManager mFragmentManager;
	private HashMap<String, Tab> mTabs = new HashMap<String, Tab>();
	private Tab mTabNow;
	private CommonTabView mLuoHeBtn;
	private CommonTabView mRecommendBtn;
	private CommonTabView mChatBtn;
	private CommonTabView mFindBtn;
	private CommonTabView mMineBtn;
	private boolean mIsExit;

	public static Intent getStartIntent(Context context) {
		Intent intent = new Intent(context, MainTabActivity.class);
		return intent;
	}

	@Override
	protected void init(Bundle savedInstanceState) {
		mContext = MainTabActivity.this;
		mFragmentManager = getSupportFragmentManager();

		initTabs();
		selectFirstTab(LuoheTabFragment.class);

	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.main_tab_activity;
	}

	@Override
	protected void onResume() {
		super.onResume();
		EventBusControl.registerSticky(this);
	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	public Fragment getCurrentFragment() {
		return mTabNow.getFragment();
	}

	private void initTabs() {// todo
		mLuoHeBtn = new CommonTabView(this, "落和", R.drawable.tab_luohe, LuoheTabFragment.class);
		mRecommendBtn = new CommonTabView(this, "推荐", R.drawable.tab_recommend, RecommendTabFragment.class);
		mChatBtn = new CommonTabView(this, "聊天", R.drawable.tab_chat, ChatTabFragment.class);
		mFindBtn = new CommonTabView(this, "发现", R.drawable.tab_find, FindTabFragment.class);
		mMineBtn = new CommonTabView(this, "我", R.drawable.tab_wode, MineTabFragment.class);
		addTab(mLuoHeBtn);
		addTab(mRecommendBtn);
		addTab(mChatBtn);
		addTab(mFindBtn);
		addTab(mMineBtn);
		final LoginHelper loginHelper = new LoginHelper(this);
		View.OnClickListener btnCallBack = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getTag() != null) {
					final Class clazz = (Class) v.getTag();
					if (clazz == MineTabFragment.class) {
						loginHelper.doLogin(new LoginHelper.ILoginListener() {
							@Override
							public void onLogin() {
								selectTab(clazz);
							}
						});
					} else if (clazz == ChatTabFragment.class) {
						loginHelper.doLogin(new LoginHelper.ILoginListener() {
							@Override
							public void onLogin() {
								selectTab(clazz);
							}
						});
					} else if (clazz == FindTabFragment.class) {
						loginHelper.doLogin(new LoginHelper.ILoginListener() {
							@Override
							public void onLogin() {
								selectTab(clazz);
							}
						});
					} else {
						selectTab(clazz);
					}
				}

			}
		};
		mTabWidget.setDividerDrawable(null);
		for (int i = 0; i < mTabWidget.getTabCount(); i++) {
			mTabWidget.getChildTabViewAt(i).setOnClickListener(btnCallBack);
		}
	}

	public void onSetActionBarTitle(String clazz) {
		getTitlebar().removeAllActions();
		if (clazz.equals(LuoheTabFragment.class.getSimpleName())) {
			getTitlebar().setTitle("落和");
			getTitlebar().addAction(new TitleBar.TextAction("怎么玩") {
				@Override
				public void performAction(View view) {
				startActivity(new Intent(getContext(), HowToPlayActivity.class));
				}

				@Override
				public int getBackground() {
					return 0;
				}
			});

		} else if (clazz.equals(RecommendTabFragment.class.getSimpleName())) {
			getTitlebar().setTitle("推荐");

		} else if (clazz.equals(FindTabFragment.class.getSimpleName())) {
			getTitlebar().setTitle("发现");

		} else if (clazz.equals(ChatTabFragment.class.getSimpleName())) {
			getTitlebar().setTitle("聊天");

		} else if (clazz.equals(MineTabFragment.class.getSimpleName())) {
			getTitlebar().setTitle("我的");
			getTitlebar().addAction(new TitleBar.TextAction("召唤") {
				@Override
				public void performAction(View view) {
					List<ShareItem> shares = new ArrayList<>();
					ShareHelper.preShareWindow(getContext(), shares, new ShareHelper.ShareClickListener() {
						@Override
						public void onItemClick(int position, Object object) {
							ShareUtils.showShare(position + 2, null, null, null, null, null);
						}
					});
					ShareHelper.showPopWindow(getContext(), view);
				}

				@Override
				public int getBackground() {
					return 0;
				}
			});
		}
	}

	private void selectFirstTab(Class clazz) {
		selectTab(clazz);
	}

	private void addTab(CommonTabView tabView) {
		Tab tab = new Tab(tabView.getFragmentClazz(), tabView.getView());
		mTabs.put(tab.tag, tab);
		mTabWidget.addView(tabView.getView());
	}

	public void selectTab(Class tabTagCls) {
		Tab tab = mTabs.get(tabTagCls.getName());
		if (mTabNow == tab) {// 如果选中的Tab没变,直接返回
			return;
		}
		// 开始一个Fragment事务
		FragmentTransaction transaction = mFragmentManager.beginTransaction();
		if (mTabNow != null) {// 隐藏当前Tab
			mTabNow.hide(transaction);
		}
		// 显示Tab
		tab.show(transaction);
		transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
		// 开始执行Fragment事务
		transaction.commitAllowingStateLoss();
		// 立即执行事务
		mFragmentManager.executePendingTransactions();
		mTabNow = tab;
		onSetActionBarTitle(tabTagCls.getSimpleName());
	}

	/**
	 * 在Fragment显示前做相应的处理
	 *
	 * @param fragment
	 */
	private void beforeShowFragment(Fragment fragment) {
		// do nothing
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (!mIsExit) {
				ToastManager.getInstance(mContext).makeToast(getResources().getString(R.string.exit), false, false);
				mIsExit = true;
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Subscriber(tag = EventBusControl.LOG_OUT)
	public void onEvent(Object object) {
		Log.e(TAG, "log_out");
		selectTab(LuoheTabFragment.class);
		EventBusControl.removeSticky(object.getClass(), EventBusControl.LOG_OUT);
	}

	/**
	 * Wyatt疑问：FragmentTransaction本身就有fragment.getTag()，popBackStack(),
	 * findFragmentTag()等功能
	 * Fragment不能独立存在，它必须嵌入到activity中，而且Fragment的生命周期直接受所在的activity的影响
	 */

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		mIsExit = false;
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * Tab类
	 */
	private class Tab {

		/**
		 * Tab对应的tag
		 */
		private String tag;

		private View view;
		private Fragment fragment;

		private Tab(Class clazz, View view) {
			this.tag = clazz.getName();
			this.view = view;
			fragment = Fragment.instantiate(mContext, tag);
		}

		/**
		 * 显示Tab
		 *
		 * @param transaction
		 */
		private void show(FragmentTransaction transaction) {
			view.setSelected(true);
			Fragment showFragment = this.fragment;
			beforeShowFragment(showFragment);
			if (showFragment.isAdded()) {// 已添加过,直接显示Fragment
				transaction.show(showFragment);
			} else if (!showFragment.isAdded()) {// 未添加过,加入Fragment
				transaction.add(R.id.fragment_layout, showFragment, this.tag);
			}
		}

		/**
		 * 隐藏Tab
		 *
		 * @param transaction
		 */
		private void hide(FragmentTransaction transaction) {
			// 取消原Tab对应视图的选中状态
			view.setSelected(false);
			transaction.hide(mTabNow.getFragment());
			// mTabNow.getFragment().onPause();
		}

		/**
		 * 反回当前Tab显示中的Fragment
		 *
		 * @return
		 */
		private Fragment getFragment() {
			return this.fragment;
		}

	}

}
