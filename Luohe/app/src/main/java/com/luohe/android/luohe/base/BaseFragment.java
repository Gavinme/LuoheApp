package com.luohe.android.luohe.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.net.http.OnReceive;
import com.luohe.android.luohe.utils.LoadingHelper;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/2/20.
 */
public abstract class BaseFragment extends Fragment implements IObserable {
    ObserableDelegate mObserableDelegate = new ObserableDelegate();

    public Scheduler getNewThread() {
        return Schedulers.io();
    }

    public Scheduler getMainThread() {
        return AndroidSchedulers.mainThread();

    }

    private LoadingHelper mLoadingHelper;

    protected LoadingHelper initLoadingHelper(View contentView) {
        if (mLoadingHelper == null) {
            mLoadingHelper = new LoadingHelper(contentView);
        }
        return mLoadingHelper;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("BaseFragment", this.getClass().getSimpleName() + ":onHiddenChanged state" + hidden);
    }

    /**
     * you must call initLoadingHelper first,otherwise it will throw error
     * exception ,in base fragment will call initLoadingHelper default pass
     * getview as the params
     *
     * @return
     */
    public LoadingHelper getLoadingHelper() {
        if (mLoadingHelper == null)
            throw new IllegalArgumentException("you must call the method initLoadingHelper");
        return mLoadingHelper;
    }

    /**
     * @return the default loading helper which replaces the fragment content
     * view;
     */
    public LoadingHelper getDefaultLoadHelper() {
        return initLoadingHelper(getView());
    }

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

    @Override
    public void addSubscription(Subscription s) {
        mObserableDelegate.addSubscription(s);
    }

    @Override
    public void cancelAllSubscript() {
        mObserableDelegate.cancelAllSubscript();
    }

    @Override
    public <T> Subscription onHandleNetRequest(Observable<T> observable, OnReceive<T> onReceive) {
        return mObserableDelegate.onHandleNetRequest(observable, onReceive);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelAllSubscript();
    }

}
