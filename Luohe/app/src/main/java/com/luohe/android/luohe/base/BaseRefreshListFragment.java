package com.luohe.android.luohe.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.widget.recycleview.inner.EndlessRecyclerOnScrollListener;
import com.luohe.android.luohe.widget.recycleview.inner.HeaderAndFooterRecyclerViewAdapter;
import com.luohe.android.luohe.widget.recycleview.inner.RecyclerViewUtils;
import com.luohe.android.luohe.widget.recycleview.utils.RecyclerViewStateUtils;
import com.luohe.android.luohe.widget.recycleview.weight.LoadingFooter;
import com.luohe.android.luohe.widget.recycleview.weight.SampleHeader;

import java.util.List;

/**
 * Created by GanQuan on 16/3/26.子类请不要重写layoutId方法
 */
public abstract class BaseRefreshListFragment<T> extends BaseListFragment<T> implements SwipeRefreshLayout.OnRefreshListener {
    HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    int TOTAL_COUNTER = Integer.MAX_VALUE;
    int m_SIZE = 0;
    int m_PAGE_SIZE = 10;//the default size
    private AdapterManger<T> mAdapterManger;

    @Override
    protected void onInit(View view, RecyclerView recyclerView) {
        initAdapterManager();
        getRecyclerView().setLayoutManager(new LinearLayoutManager(getRecyclerView().getContext()));
        getSwipeRefreshLayout().setEnabled(true);
        getSwipeRefreshLayout().setOnRefreshListener(this);
        RecyclerViewUtils.setHeaderView(getRecyclerView(), new SampleHeader(getActivity()));
        getRecyclerView().addOnScrollListener(mOnScrollListener);
        initAdapter();
    }

    /**
     * 首次刷新时调用
     */
    protected void refreshData() {
        getSwipeRefreshLayout().setRefreshing(true);
        onRefresh();
    }

    private void initAdapter() {
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(getAdapter());
        getRecyclerView().setAdapter(mHeaderAndFooterRecyclerViewAdapter);
    }

    private void initAdapterManager() {
        mAdapterManger = new AdapterManger<T>() {
            @Override
            public void initListToAdapter(List<T> list) {
                initList(list);
            }

            @Override
            public void addListToAdapter(List<T> list) {
                addList(list);
            }

            @Override
            public void clearListToAdapter() {
                clearList();
            }
        };
    }

    public void setPageSize(int pageSize) {
        this.m_PAGE_SIZE = pageSize;
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);
            if (getSwipeRefreshLayout().isRefreshing()) {
                Log.e("gq", "refresh is loading");
                return;
            }
            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(getRecyclerView());
            if (state == LoadingFooter.State.Loading) {
                Log.d("gq", "the state is Loading, just wait..");
                return;
            }
            Log.e("gq_onLoadNextPage", LoadingFooter.State.Loading.toString());

            if (m_SIZE < TOTAL_COUNTER) {
                // loading more
                RecyclerViewStateUtils
                        .setFooterViewState(getActivity(), getRecyclerView(), m_PAGE_SIZE, LoadingFooter.State.Loading, null);
                onLoadMore(mAdapterManger);
            } else {
                //the end

                RecyclerViewStateUtils.removeFooterView(getRecyclerView());
            }
        }
    };


    @Override
    public void onRefresh() {
        onPullDown(mAdapterManger);
    }

    /**
     * call this method when over load
     */
    public void notifyLoadComplete(int errorCode) {
        if (getSwipeRefreshLayout().isRefreshing()) {//刷新后
            getSwipeRefreshLayout().setRefreshing(false);
            if (errorCode != Result.OK) {
                //todo error refresh
            }
        } else {//加载更多后

            Log.e("gq", "setLoadMore" + LoadingFooter.State.Normal);
            RecyclerViewStateUtils.setFooterViewState(getRecyclerView(), LoadingFooter.State.Normal);

            if (errorCode != Result.OK) {
                setLoadMoreError(true);
                RecyclerViewStateUtils.setFooterViewState(getActivity(), getRecyclerView(), m_PAGE_SIZE, LoadingFooter.State.NetWorkError, mFooterClick);
            }
            //up todo
            //do nothing
        }
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), getRecyclerView(), m_PAGE_SIZE, LoadingFooter.State.Loading, null);
            onLoadMore(mAdapterManger);
        }
    };

    protected abstract void onPullDown(AdapterManger<T> mAdapterManger);

    protected abstract void onLoadMore(final AdapterManger<T> adapter);

    boolean mLoadMoreError;

    private boolean isLoadMoreError() {
        return mLoadMoreError;
    }

    private void setLoadMoreError(boolean bool) {
        this.mLoadMoreError = bool;
    }
}
