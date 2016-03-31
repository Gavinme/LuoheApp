package com.luohe.android.luohe.recommond;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androidplus.util.LayoutUtil;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.base.BaseListFragment;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.utils.LocalDisplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by GanQuan on 16/3/13.
 */
public class RecommendListFragment extends BaseRefreshListFragment<RecommendListFragment.RecomBean> {
    Handler mHanlder = new Handler();

    @Override
    protected void onInit(View view, RecyclerView recyclerView) {
        super.onInit(view, recyclerView);
        recyclerView.setPadding(0, LayoutUtil.GetPixelByDIP(getActivity(), 8), 0, 0);
        mHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshData();
            }
        }, 1000);

    }

    private List<RecomBean> createData() {
        List<RecomBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new RecomBean());
        }
        return list;
    }


    @Override
    protected Class<? extends BaseRecyclerViewAdapter<RecomBean>> onGetAdapterType() {
        return RecommAdapter.class;
    }


    @Override
    protected void onPullDown(AdapterManger<RecomBean> mAdapterManger) {
        mAdapterManger.initListToAdapter(createData());
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
    protected void onListItemClick(View view, int adapterPosition) {
        super.onListItemClick(view, adapterPosition);
        startActivity(new Intent(getActivity(), PreviewArticalActivity.class));
    }

    static class RecommAdapter extends BaseRecyclerViewAdapter<RecomBean> {

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
