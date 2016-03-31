package com.luohe.android.luohe.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.utils.InstanceUtil;
import com.luohe.android.luohe.widget.recycleview.inner.HeaderAndFooterRecyclerViewAdapter;
import com.luohe.android.luohe.widget.recycleview.inner.RecyclerViewUtils;

import java.util.List;

/**
 * Created by GanQuan on 16/3/25.a simple recycleview
 * 如果adapter使用带有头部adapter需要消除头部的影响
 */
public abstract class BaseListFragment<T> extends BaseFragment implements BaseRecyclerViewAdapter.OnItemClickListener {
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipe;
    }

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipe;
    private BaseRecyclerViewAdapter<T> mAdapter;
    protected static final int OP_INIT_LIST = 100;
    protected static final int OP_ADD_LIST = 101;
    protected static final int OP_CLEAR_LIST = 102;

    @Override
    protected void init(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        mSwipe.setColorSchemeColors(colors);
        mSwipe.setEnabled(false);//默认禁止下拉
        mAdapter = instanceAdapter(onGetAdapterType());//init adapter
        mAdapter.setOnItemClickLitener(this);
    }

    @Override
    public final void onItemClick(View view, final int position) {
        if (getRecyclerView().getAdapter() instanceof HeaderAndFooterRecyclerViewAdapter) {
            onListItemClick(view, RecyclerViewUtils.getAdapterPosition(getRecyclerView(), position));
        }
    }

    protected void onListItemClick(View view, int adapterPosition) {
        //click
    }

    protected abstract void onInit(View container, RecyclerView recyclerView);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onInit(getView(), mRecyclerView);

    }

    protected abstract Class<? extends BaseRecyclerViewAdapter<T>> onGetAdapterType();

    private BaseRecyclerViewAdapter<T> instanceAdapter(Class<? extends BaseRecyclerViewAdapter<T>> cls) {
        return InstanceUtil.getInstance(cls, new Class[]{Context.class}, new Object[]{getActivity()});
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.fragment_list_layout;
    }

    protected BaseRecyclerViewAdapter getAdapter() {
        return mAdapter;
    }

    public void initList(List<T> list) {
        initInnerList(list);

    }

    protected void initInnerList(List<T> list) {
        if (list != null && list.size() > 0) {
            handleAdapterOp(list, OP_INIT_LIST);
        }
    }

    protected void addList(List<T> list) {
        if (list != null && list.size() > 0) {
            handleAdapterOp(list, OP_ADD_LIST);
        }
    }


    protected void clearList() {
        if (mAdapter != null) {
            mAdapter.clearList();
        }
    }

    private void handleAdapterOp(List<T> items, int op) {
        switch (op) {
            case OP_INIT_LIST:
                mAdapter.initList(items);
                break;
            case OP_ADD_LIST:
                mAdapter.addList(items);
                break;
            case OP_CLEAR_LIST:
                mAdapter.clearList();
                break;
        }
    }

    public interface AdapterManger<T> {
        void initListToAdapter(List<T> list);

        void addListToAdapter(List<T> list);

        void clearListToAdapter();
    }
}
