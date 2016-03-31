package com.luohe.android.luohe.find;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.DividerDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * rank
 */
public class FindRankFragment extends BaseFragment {
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private RankAdapter mListAdapter;

    @Override
    protected void init(View view) {
        initViews();
        view.post(new Runnable() {
            @Override
            public void run() {
                mListAdapter = new RankAdapter(getActivity());
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
                mRecyclerView.addItemDecoration(new DividerDecoration(getActivity()));
                mRecyclerView.setAdapter(mListAdapter);
                mListAdapter.setOnItemClickLitener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                RankBean bean = mListAdapter.getItemBean(position);
//                startActivity(new Intent(getActivity(), PreviewArticalActivity.class));
                    }
                });
                mListAdapter.addList(createData());
            }
        });
    }

    private List<RankBean> createData() {
        List<RankBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new RankBean());
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

    @BindLayout(id = R.layout.item_rank)
    static class ViewHolderRank extends BaseRecyclerViewAdapter.BaseViewHolder<RankBean> {


        public ViewHolderRank(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(RankBean bean, int position, Context context) {

        }
    }

    public static class RankBean implements Serializable {
    }

    private class RankAdapter extends BaseRecyclerViewAdapter<RankBean> {

        public RankAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
            viewBundles.add(ViewHolderRank.class);
//            viewBundles.add(new ViewBundle(R.layout.recommen_list_item, ViewHolderRecommend.class));
        }
    }
}
