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
import com.luohe.android.luohe.common.MyApplication;
import com.luohe.android.luohe.common.RetrofitImp;
import com.luohe.android.luohe.widget.CommonTabView;

import java.util.HashMap;

import butterknife.Bind;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

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
        setSwipeBackEnable(false);
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

    private void initTabs() {//todo
        mLuoHeBtn = new CommonTabView(this, "落和",
                R.drawable.tab_luohe, LuoheTabFragment.class);
        mRecommendBtn = new CommonTabView(this, "推荐",
                R.drawable.tab_recommend, RecommendTabFragment.class);
        mChatBtn = new CommonTabView(this, "聊天",
                R.drawable.tab_chat, ChatTabFragment.class);
        mFindBtn = new CommonTabView(this, "发现",
                R.drawable.tab_find, FindTabFragment.class);
        mMineBtn = new CommonTabView(this, "我",
                R.drawable.tab_wode, MineTabFragment.class);
        addTab(mLuoHeBtn);
        addTab(mRecommendBtn);
        addTab(mChatBtn);
        addTab(mFindBtn);
        addTab(mMineBtn);
        View.OnClickListener btnCallBack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    Class clazz = (Class) v.getTag();
                    selectTab(clazz);
                }

            }
        };
        mTabWidget.setDividerDrawable(null);
        for (int i = 0; i < mTabWidget.getTabCount(); i++) {
            mTabWidget.getChildTabViewAt(i).setOnClickListener(btnCallBack);
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
        transaction.commit();
        // 立即执行事务
        mFragmentManager.executePendingTransactions();
        mTabNow = tab;
    }

    /**
     * 在Fragment显示前做相应的处理
     *
     * @param fragment
     */
    private void beforeShowFragment(Fragment fragment) {
        //do nothing
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
