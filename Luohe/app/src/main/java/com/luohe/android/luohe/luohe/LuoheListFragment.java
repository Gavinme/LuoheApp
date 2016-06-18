package com.luohe.android.luohe.luohe;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseListFragment;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.common.TitleViewPagerAdapter;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.LuoheTagBean;
import com.luohe.android.luohe.net.model.LuoheWrapBean;
import com.luohe.android.luohe.recommond.CommentListActivity;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.ImageUtils;
import com.luohe.android.luohe.utils.ToastUtil;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/3/6.
 */
public class LuoheListFragment extends BaseRefreshListFragment<LuoheWrapBean> {
    private final String TAG = LuoheListFragment.class.getSimpleName();
    private int tagId;
    private CommonSearchActivity.SearchFragmentImp mSearchFragmentImp = new CommonSearchActivity.SearchFragmentImp();

    public LuoheListFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBusControl.register(this);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusControl.unregister(this);
    }

    @Override
    protected void onInit(View view, RecyclerView recyclerView) {
        super.onInit(view, recyclerView);

        if (isSearch()) {

        } else {
            LuoheTagBean tagBean = (LuoheTagBean) getArguments().getSerializable(TitleViewPagerAdapter.BUNDLE_KEY);
            if (tagBean != null)
                tagId = tagBean.baseId;
            Log.e("LuoheListFragment", "tagId:" + tagId);

        }
        setListMode(MODE_DISABLE);
        refreshData();
        getLoadingHelper().setEnable(true);
        getLoadingHelper().showLoadingView();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume" + tagId);

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause" + tagId);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(TAG, "onHiddenChanged:" + hidden);
    }

    private boolean isSearch() {
        return mSearchFragmentImp.isSearch(this);
    }

    public void setSearchArgument(String key) {
        setArguments(mSearchFragmentImp.getKeyBundle(key));
    }

    @Override
    protected Class<? extends BaseRecyclerViewAdapter<LuoheWrapBean>> onGetAdapterType() {
        return LuoheListAdapter.class;
    }

    @Override
    protected void onPullDown(final AdapterManger<LuoheWrapBean> mAdapterManger) {
        if (isSearch()) {
            ApiLoader.getApiService().searchLuoheList(mSearchFragmentImp.getSearchKey(this))
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CommonSubscriber<Result<List<LuoheWrapBean>>>(getActivity()) {
                        @Override
                        public void onSuccess(Result<List<LuoheWrapBean>> listResult) {
                            if (listResult != null && listResult.getResult() != null) {

                                mAdapterManger.initListToAdapter(handlerList(listResult.getResult()));
                                getLoadingHelper().showContentView();
                                getLoadingHelper().setEnable(false);
                            } else {
                                getLoadingHelper().showNetworkError();
                            }
                        }
                    }.onError(new CommonSubscriber.ErrorHandler() {
                        @Override
                        public void onError(Throwable e) {
                            getLoadingHelper().showNetworkError();
                            getSwipeRefreshLayout().setRefreshing(false);

                        }
                    }));
            return;
        }// todo
        loadListData(mAdapterManger);
        ApiLoader.getApiService().fallOrder(tagId, "0", 0).subscribeOn(getNewThread()).observeOn(getMainThread())
                .subscribe(new CommonSubscriber<Result<List<LuoheWrapBean>>>(getActivity()) {

                    @Override
                    public void onSuccess(Result<List<LuoheWrapBean>> listResult) {
                        if (listResult != null && listResult.getResult() != null) {

                            mAdapterManger.initListToAdapter(handlerList(listResult.getResult()));
                            getLoadingHelper().showContentView();
                            getLoadingHelper().setEnable(false);
                        } else {
                            getLoadingHelper().showNetworkError();
                        }
                    }
                }.onError(new CommonSubscriber.ErrorHandler() {
                    @Override
                    public void onError(Throwable e) {
                        getLoadingHelper().showNetworkError();
                        getSwipeRefreshLayout().setRefreshing(false);

                    }
                }));

    }

    private void loadListData(final AdapterManger<LuoheWrapBean> mAdapterManger) {
        if (tagId == TitleViewPagerAdapter.HUIZONG) {
            LogUtils.e("fxl", tagId + "");
            ApiLoader.getApiService().allFallOrder("0").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CommonSubscriber<Result<List<LuoheWrapBean>>>(getActivity()) {
                        @Override
                        public void onSuccess(Result<List<LuoheWrapBean>> listResult) {
                            if (listResult != null && listResult.getResult() != null) {
                                mAdapterManger.initListToAdapter(handlerList(listResult.getResult()));
                            }

                            getLoadingHelper().showContentView();
                            getLoadingHelper().setEnable(false);
                        }
                    }.onError(new CommonSubscriber.ErrorHandler() {
                        @Override
                        public void onError(Throwable e) {
                            getLoadingHelper().showNetworkError();
                        }
                    }));
        }
    }

    private List<LuoheWrapBean> handlerList(List<LuoheWrapBean> result) {

        List<LuoheWrapBean> list = new ArrayList<>();
        for (LuoheWrapBean luoheWrapBean : result) {
            list.add(luoheWrapBean);
            if (luoheWrapBean.getTitles(luoheWrapBean.userId) != null) {
                list.addAll(luoheWrapBean.getTitles(luoheWrapBean.userId));
            }
        }
        return list;
    }

    @Override
    protected void onLoadMore(final AdapterManger<com.luohe.android.luohe.net.model.LuoheWrapBean> adapter) {

    }

    @Subscriber(tag = EventBusControl.WRITE_THEME_SUC)
    public void onEvent(Object busEvent) {
        LogUtils.e("gq", "refresh");
        refreshData();

    }

    @Override
    protected void onListItemClick(LuoheWrapBean bean, View view, int adapterPosition) {
        if (bean.type == 0) {// luohe

            startActivity(LuoheDetailActivity.getStartIntent(getActivity(), bean));
        } else {// subject
            // // TODO: 16/5/25
        }

    }

    public static class LuoheListAdapter extends BaseRecyclerViewAdapter<LuoheWrapBean> {

        public LuoheListAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemViewType(int position) {
            return getItemBean(position).type;
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
            viewBundles.add(ViewHolderItem.class);
            viewBundles.add(VHInner.class);
        }

    }

    @BindLayout(id = R.layout.luohe_adapter_item)
    static class ViewHolderItem extends BaseRecyclerViewAdapter.BaseViewHolder<LuoheWrapBean> {
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.state)
        TextView state;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.divider)
        View divider;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.desc)
        TextView desc;
        @Bind(R.id.commit)
        TextView commit;
        @Bind(R.id.luohe_divider)
        View luohe_divider;

        public ViewHolderItem(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(final LuoheWrapBean bean, int position, final Context context) {

            if (UserInfoUtil.getInstance().IsLogin() && UserInfoUtil.getInstance().getUserId() == bean.userId) {
                LogUtils.e("fxl", bean.userId + "uid:" + UserInfoUtil.getInstance().getUserInfo().getUid());
                if (bean.fallOrderState == 0 && bean.userId == UserInfoUtil.getInstance().getUserInfo().getUid()) {
                    commit.setText("提前结束");
                    commit.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_yellow));
                    commit.setVisibility(View.VISIBLE);
                } else {
                    commit.setVisibility(View.GONE);
                }
            } else {
                commit.setText("发表问令");
                commit.setBackgroundResource(R.drawable.rect_blue);
                commit.setVisibility(View.VISIBLE);
            }
            if (position == 0) {
                luohe_divider.setVisibility(View.GONE);

            } else {
                luohe_divider.setVisibility(View.VISIBLE);
            }

            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commit.getText().equals("提前结束")) {
                        final AlertDialog ad=new AlertDialog.Builder(getContext()).create();
                        ad.setTitle("落和令");
                        ad.setIcon(R.drawable.ic_launcher);
                        ad.setMessage("确认要提前结束此落和令吗？");
                        ad.setButton(Dialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        ad.setButton(Dialog.BUTTON_POSITIVE, "确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeLhl(bean.id);
                                ad.dismiss();
                            }
                        });
                        ad.show();
                    } else {
                        context.startActivity(WriteThemeActivity.getStartIntent(context, bean.id));
                    }

                }
            });

            ImageUtils.displayRoundImage(bean.headUrl, avatar);
            state.setText(bean.getAwardTitle() + "");
            name.setText(bean.userName);
            LogUtils.e("fxl","落和令时间戳:"+bean.fallOrderPublish+"转换时间:"+bean.getTime()    );
            time.setText(bean.getTime());
            title.setText(bean.fallOrderName);
            desc.setText(bean.fallOrderDes);

        }

        private void closeLhl(int id) {
            ApiLoader.getApiService().closeLhl(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CommonSubscriber<Result>(getContext()) {
                        @Override
                        public void onSuccess(Result result) {
                        ToastUtil.showToast("提前结束落和令成功，如果还有显示请刷新界面！");
                        }
                    });
        }


    }

    @BindLayout(id = R.layout.luohe_inner_item)
    static class VHInner extends BaseRecyclerViewAdapter.BaseViewHolder<LuoheWrapBean> {
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.desc)
        TextView desc;
        @Bind(R.id.tv_time)
        TextView tv_time;
        @Bind(R.id.tv_award)
        TextView tv_award;
        @Bind(R.id.tv_comment)
        TextView tv_comment;
        @Bind(R.id.tv_coin)
        TextView tvCoin;
        @Bind(R.id.divider)
        View divider;
        @Bind(R.id.left_icon)
        TextView leftIcon;

        public VHInner(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(final LuoheWrapBean bean, int position, final Context context) {

            final LuoheWrapBean.SubjectBean subjectBean = bean.subjectBean;
            if (UserInfoUtil.getInstance().IsLogin() && UserInfoUtil.getInstance().getUserId() == subjectBean.userId) {

            } else {
            }
            if (UserInfoUtil.getInstance().checkUid(subjectBean.atLuoheUid)) {// 落和是我写的
                tv_award.setText("写文章");
                tv_award.setVisibility(View.VISIBLE);

            } else {
                if (UserInfoUtil.getInstance().checkUid(subjectBean.userId)) {// 主题是我写的
                    tv_award.setText("查看文章");
                    tv_award.setVisibility(View.VISIBLE);
                } else {// 主题是别人写的
                    tv_award.setText("加赏");
                    tv_award.setVisibility(View.VISIBLE);

                }

            }
            loadRoundImagUrl(avatar, subjectBean.headUrl);
            name.setText(subjectBean.userName);
            title.setText(subjectBean.titleName);
            desc.setText(subjectBean.titleDes);
            tvCoin.setText(subjectBean.artValue + "");
            tv_time.setText(subjectBean.getTime());
            tv_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(CommentListActivity.getStartIntent(context, subjectBean.titleId));
                }
            });
            if (subjectBean.pos == 0) {
                leftIcon.setText("必");
                leftIcon.setBackgroundResource(R.drawable.rect_blue_bound);
            } else {
                leftIcon.setText("");
                leftIcon.setBackgroundResource(R.drawable.luohe_rank_left);
            }

        }
    }


}
