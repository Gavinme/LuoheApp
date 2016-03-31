package com.luohe.android.luohe.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.test.testActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by GanQuan on 16/2/20.
 */
public abstract class BaseFragment extends Fragment {
    CompositeSubscription mSubscriptionGroup;//用于取消异步任务的会调

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (onBindLayoutId() == 0)
            return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(onBindLayoutId(), null);
        ButterKnife.bind(this, view);
        init(view);
        return view;
    }

    protected abstract void init(View view);


    protected abstract int onBindLayoutId();

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected TitleBar getTitleBar() {
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            return activity.getTitlebar();
        }
        return null;
    }

    protected void addSubscripter(Subscription s) {
        if (mSubscriptionGroup == null) {
            mSubscriptionGroup = new CompositeSubscription();
        }
        mSubscriptionGroup.add(s);
    }

    protected void cancelAllSubscript() {
        if (mSubscriptionGroup != null)
            mSubscriptionGroup.clear();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }
}
