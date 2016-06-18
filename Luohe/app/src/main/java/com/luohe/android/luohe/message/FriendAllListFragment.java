package com.luohe.android.luohe.message;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.AttenBean;
import com.luohe.android.luohe.utils.CharacterParser;
import com.luohe.android.luohe.view.SideBar;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.DividerDecoration;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.RecyclerArrayAdapter;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;

public class FriendAllListFragment extends BaseFragment {
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecycleView;
    @Bind(R.id.side_bar)
    SideBar mSideBar;
    private LinearLayoutManager mLayoutManager;
    private AllFriendsAdapter mAdapter;


    @Override
    protected void init(View view) {
        initView();
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.fragment_friend_all_list;
    }

    private void initView() {
        initAllFriends();
    }

    private void initAllFriends() {
        mRecycleView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(mLayoutManager);

        mAdapter = new AllFriendsAdapter();
        //mAdapter.addAll(ContactManager.getContacts(getActivity()));
        mRecycleView.setAdapter(mAdapter);

        StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
        mRecycleView.addItemDecoration(new DividerDecoration(getActivity()));
        mRecycleView.addItemDecoration(headersDecor);

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                mLayoutManager.scrollToPositionWithOffset(mAdapter.getPositionFromId(s), 0);
            }
        });
        initLoadingHelper(refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllFriends();
            }
        });
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAllFriends();
            }
        }, 200);
    }

    private void getAllFriends() {
        refreshLayout.setRefreshing(true);
        ApiLoader.getApiService().attenList(1, 4, 0).subscribeOn(getNewThread())
                .observeOn(getMainThread()).subscribe(new CommonSubscriber<Result<List<AttenBean>>>(getActivity()) {
            @Override
            public void onSuccess(Result<List<AttenBean>> listResult) {
                if (listResult != null && listResult.getResult() != null) {
                    if (listResult.getResult().size() != 0) {
                        getLoadingHelper().showContentView();
                        mAdapter.clear();
                        mAdapter.addAll(listResult.getResult());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        getLoadingHelper().showDefaultEmptyView();
                    }
                } else {
                    getLoadingHelper().showNetworkError();

                }
                refreshLayout.setRefreshing(false);

            }

        }.onError(new CommonSubscriber.ErrorHandler() {
            @Override
            public void onError(Throwable e) {
                getLoadingHelper().showNetworkError();
                refreshLayout.setRefreshing(false);

            }
        }));
    }

    private class AllFriendsAdapter extends RecyclerArrayAdapter<AttenBean, RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = View.inflate(viewGroup.getContext(), R.layout.layout_manage_friend_list_item, null);

            return new RecyclerView.ViewHolder(view) {
            };

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            onBindUserViewHolder(viewHolder, i);
        }

        private void onBindUserViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            TextView textView = (TextView) viewHolder.itemView.findViewById(R.id.tv_name);
            ImageView imageView = (ImageView) viewHolder.itemView.findViewById(R.id.iv_avatar);
            textView.setText(getItem(i).nickName);
            ImageLoader.getInstance().displayImage(getItem(i).headUrl, imageView);
        }

        private String getHeaderString(int position) {
            if (position >= getItemCount())
                return "";
            String id = CharacterParser.getInstance().getSpelling(getItem(position).nickName).substring(0, 1).toUpperCase();
            if (!id.matches("[A-Z]"))
                id = "#";
            return id;
        }

        @Override
        public long getHeaderId(int position) {
            if (position >= getItemCount())
                return -1;
            if (getItemViewType(position) > 0)
                return getHeaderString(position).charAt(0);
            else
                return 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_header, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView textView = (TextView) holder.itemView;
            if (getItemViewType(position) > 0)
                textView.setText(getHeaderString(position));
            else
                textView.setText("Friend Requests");
        }

        public int getPositionFromId(String s) {
            if (s.equals("#"))
                return 0;
            for (int i = 0; i < items.size(); i++) {
                if (getHeaderId(i) >= s.charAt(0))
                    return i;
            }
            return items.size() - 1;
        }
    }

}
