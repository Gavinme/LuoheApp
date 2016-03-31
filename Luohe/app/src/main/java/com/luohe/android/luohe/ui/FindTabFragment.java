package com.luohe.android.luohe.ui;

import android.content.Intent;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.find.FindItemView;
import com.luohe.android.luohe.find.FindRankActivity;
import com.luohe.android.luohe.find.FindReactActivity;
import com.luohe.android.luohe.find.FindWishPoolActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by GanQuan on 16/2/20.
 * find
 */
public class FindTabFragment extends BaseFragment {


    @Bind(R.id.react_item)
    FindItemView reactItem;
    @Bind(R.id.wish_pool_item)
    FindItemView wishPoolItem;
    @Bind(R.id.rank_item)
    FindItemView rankItem;
    @Bind(R.id.system_msg_item)
    FindItemView systemMsgItem;

    @Override
    protected int onBindLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void init(View view) {
        initItemViews();
    }


    private void initItemViews() {
        initReact();
        initWishPool();
        initRank();
        initSystemMsg();
    }

    //系统消息
    private void initSystemMsg() {
        systemMsgItem.setLeftTextview(R.drawable.ic_find_sys_msg, getString(R.string.find_sys));
    }

    //排行榜
    private void initRank() {
        rankItem.setLeftTextview(R.drawable.ic_find_rank, getString(R.string.find_rank));

    }

    //祝愿
    private void initWishPool() {
        wishPoolItem.setLeftTextview(R.drawable.ic_find_wish_pool, getString(R.string.find_wish_pool));

    }

    //互动
    private void initReact() {
        reactItem.setLeftTextview(R.drawable.ic_find_react, getString(R.string.find_react));

    }

    @OnClick({R.id.react_item, R.id.wish_pool_item, R.id.rank_item, R.id.system_msg_item})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.react_item:
                startActivity(new Intent(getActivity(), FindReactActivity.class));
                break;
            case R.id.wish_pool_item:
                startActivity(new Intent(getActivity(), FindWishPoolActivity.class));
                break;
            case R.id.rank_item:
                startActivity(new Intent(getActivity(), FindRankActivity.class));
                break;
            case R.id.system_msg_item:
                break;
        }
    }
}
