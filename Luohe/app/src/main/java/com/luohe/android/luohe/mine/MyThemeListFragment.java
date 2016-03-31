package com.luohe.android.luohe.mine;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GanQuan on 16/3/20.
 */
public class MyThemeListFragment extends BaseRefreshListFragment<MyThemeListFragment.RecomBean> {
    Handler mHanlder = new Handler();


    private List<RecomBean> createData() {
        List<RecomBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new RecomBean());
        }
        return list;
    }

    @Override
    protected void onInit(View container, RecyclerView recyclerView) {
        super.onInit(container, recyclerView);
        mHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshData();
            }
        }, 1000);

    }

    @Override
    protected void onPullDown(AdapterManger<RecomBean> mAdapterManger) {
        mHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyLoadComplete(0);
            }
        }, 1000);

    }

    @Override
    protected void onLoadMore(final AdapterManger<RecomBean> adapter) {
        mHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addListToAdapter(createData());
                notifyLoadComplete(0);
            }
        }, 1000);
    }

    @Override
    protected Class<? extends BaseRecyclerViewAdapter<RecomBean>> onGetAdapterType() {
        return RecommAdapter.class;
    }

    private class RecommAdapter extends BaseRecyclerViewAdapter<RecomBean> {

        public RecommAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
            viewBundles.add(ViewHolderRecommend.class);
        }
    }

    @BindLayout(id = R.layout.recommen_list_item)
    static class ViewHolderRecommend extends BaseRecyclerViewAdapter.BaseViewHolder<RecomBean> {


        public ViewHolderRecommend(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(RecomBean bean, int position, Context context) {

        }
    }

    public static class RecomBean implements Serializable {
    }
}
