package com.luohe.android.luohe.find;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.recommond.PreviewArticalActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * react
 */
public class FindReactFragment extends BaseFragment {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private RecommAdapter mLuoheListAdapter;

    @Override
    protected void init(View view) {
        initViews();
        mLuoheListAdapter = new RecommAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mLuoheListAdapter);
        mLuoheListAdapter.setOnItemClickLitener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RecomBean recomBean = mLuoheListAdapter.getItemBean(position);
                startActivity(new Intent(getActivity(), PreviewArticalActivity.class));
            }
        });
        mLuoheListAdapter.addList(createData());
    }

    private List<RecomBean> createData() {
        List<RecomBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new RecomBean());
        }
        return list;
    }

    private void initViews() {
        // TODO: 16/3/26 get data
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.find_detail_list;
    }

    public void getData() {
        refreshLayout.setRefreshing(false);
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

    private class RecommAdapter extends BaseRecyclerViewAdapter<RecomBean> {

        public RecommAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
            viewBundles.add(ViewHolderRecommend.class);
//            viewBundles.add(new ViewBundle(R.layout.recommen_list_item, ViewHolderRecommend.class));
        }
    }
}
