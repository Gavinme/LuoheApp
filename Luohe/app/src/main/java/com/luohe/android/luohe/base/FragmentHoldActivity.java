package com.luohe.android.luohe.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.luohe.android.luohe.R;

/**
 * Created by Devuser on 2015/5/18. 子类的启动Intent中必须put
 * className.该类仅仅是为了去持有一个fragment对象，将所有activity转为fragment来进行处理.
 */
public abstract class FragmentHoldActivity<F extends BaseFragment> extends AppCompatActivity {
    private F fragment;


    @Override
    protected void init(Bundle savedInstanceState) {
        changeFragment(onGetFragmentClazz().getName());
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.container_activity;
    }

    /**
     * 返回F.class
     *
     * @return
     */
    protected abstract Class<F> onGetFragmentClazz();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        changeFragment(onGetFragmentClazz().getName());

    }

    private void changeFragment(String clazzName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // transaction.setCustomAnimations(R.anim.slide_left_in,
        // R.anim.slide_left_out, R.anim.slide_right_in,
        // R.anim.slide_right_out);
        fragment = (F) getSupportFragmentManager().findFragmentByTag(clazzName);
        if (fragment == null) {
            fragment = (F) Fragment.instantiate(this, clazzName);
            onSetArguments(fragment);

        }

        if (!fragment.isAdded()) {
            transaction.replace(R.id.activity_content, fragment, clazzName);
        }

        transaction.commitAllowingStateLoss();
    }

    public F getHoldFragment() {
        return this.fragment;
    }

    /**
     * 出事化fragment bundle参数
     *
     * @param fragment
     */
    protected abstract void onSetArguments(F fragment);

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getHoldFragment().onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
