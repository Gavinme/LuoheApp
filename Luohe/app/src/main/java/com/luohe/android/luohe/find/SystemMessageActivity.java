package com.luohe.android.luohe.find;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.SystemMessage;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.TimeUtils;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cegrano on 16/5/26.
 * 系统消息
 */
public class SystemMessageActivity extends AppCompatActivity {

//    @Bind(R.id.message_list)
//    RecyclerView listMessage;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private RecommAdapter mLuoheListAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle(getString(R.string.find_sys));
        getTitlebar().setDefauleBackBtn();
        initViews();
    }

    private void initViews() {
        mLuoheListAdapter = new RecommAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(mLuoheListAdapter);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 200);
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
        ApiLoader.getApiService().sysInfoList(UserInfoUtil.getInstance().getUserId()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonSubscriber<Result<List<SystemMessage>>>(this) {
            @Override
            public void onSuccess(Result<List<SystemMessage>> listResult) {
                if (listResult != null && listResult.getResult() != null) {
                    if (listResult.getResult().size() != 0) {
                        getLoadingHelper().showContentView();
                        mLuoheListAdapter.initList(listResult.getResult());
                        mLuoheListAdapter.notifyDataSetChanged();
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

    @BindLayout(id = R.layout.item_system_message)
    static class ViewHolderRecommend extends BaseRecyclerViewAdapter.BaseViewHolder<SystemMessage> {

        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.iv_avatar)
        ImageView ivAvatar;
        @Bind(R.id.tv_content)
        TextView tvContent;

        public ViewHolderRecommend(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(SystemMessage bean, int position, Context context) {
            tvContent.setText(bean.sysInfo);
            tvTime.setText(TimeUtils.getFormatTime(bean.infoTime));
        }
    }

    private class RecommAdapter extends BaseRecyclerViewAdapter<SystemMessage> {

        public RecommAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
            viewBundles.add(ViewHolderRecommend.class);
            // viewBundles.add(new ViewBundle(R.layout.recommen_list_item,
            // ViewHolderRecommend.class));
        }
    }
}
