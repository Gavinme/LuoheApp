package com.luohe.android.luohe.find;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.utils.LocalDisplay;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.DividerDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * wish pool
 */
public class FindWishPoolFragment extends BaseFragment {
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
                        RankBean bean = mListAdapter.getItemBean(position);
                        if (bean.status == 0) {
                            new WishOptDialog().show(getChildFragmentManager(), bean.status + "");
                        } else {
                            new WishOpt2Dialog().show(getChildFragmentManager(), bean.status + "");
                        }
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

    @BindLayout(id = R.layout.item_wish)
    static class ViewHolderRank extends BaseRecyclerViewAdapter.BaseViewHolder<RankBean> {


        @Bind(R.id.tv_from)
        TextView tvFrom;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_wish)
        TextView tvWish;
        @Bind(R.id.tv_status)
        TextView tvStatus;

        public ViewHolderRank(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(RankBean bean, int position, Context context) {
            tvFrom.setText(Html.fromHtml("李四赠<font color='#ff0000'>10</font>才值"));
            tvStatus.setText(bean.status == 0 ? "已接收" : "待处理");
            //noinspection deprecation
            tvStatus.setTextColor(context.getResources().getColor(bean.status == 0 ? R.color.green : R.color.red));
        }
    }

    public static class RankBean implements Serializable {
        public int status = new Random().nextInt(2);
    }

    public static class WishOptDialog extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
            dialog.setContentView(R.layout.activity_wish_menu);

            ButterKnife.bind(this, dialog);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = LocalDisplay.SCREEN_WIDTH_PIXELS;
            params.y = (LocalDisplay.SCREEN_HEIGHT_PIXELS - params.height) / 2;
            window.setAttributes(params);
            window.setWindowAnimations(R.style.DialogWindowAnim);
            return dialog;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.unbind(this);
        }

        @OnClick({R.id.accept, R.id.ignor, R.id.reply, R.id.cancel})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.accept:
                    this.dismiss();
                    break;
                case R.id.ignor:
                    this.dismiss();
                    break;
                case R.id.reply:
                    this.dismiss();
                    break;
                case R.id.cancel:
                    this.dismiss();
                    break;
            }
        }
    }

    public static class WishOpt2Dialog extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
            dialog.setContentView(R.layout.activity_wish_did_menu);

            ButterKnife.bind(this, dialog);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = LocalDisplay.SCREEN_WIDTH_PIXELS;
            params.y = (LocalDisplay.SCREEN_HEIGHT_PIXELS - params.height) / 2;
            window.setAttributes(params);
            window.setWindowAnimations(R.style.DialogWindowAnim);
            return dialog;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.unbind(this);
        }

        @OnClick({R.id.reply, R.id.cancel})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.reply:
                    this.dismiss();
                    break;
                case R.id.cancel:
                    this.dismiss();
                    break;
            }
        }
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
