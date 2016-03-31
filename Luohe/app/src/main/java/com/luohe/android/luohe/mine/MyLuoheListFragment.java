package com.luohe.android.luohe.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.luohe.WriteThemeActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by GanQuan on 16/3/27.
 */
public class MyLuoheListFragment extends BaseRefreshListFragment<MyLuoheListFragment.luoheBean> {
    @Override
    protected void onInit(View view, RecyclerView recyclerView) {
        super.onInit(view, recyclerView);
        getTitleBar().setTitle(getResources().getString(R.string.my_luohe));
        mHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshData();
            }
        }, 1000);

    }

    private List<luoheBean> createData() {
        List<luoheBean> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i % 3 == 0) {
                list.add(new luoheBean(luoheBean.type_luohe));
            } else {
                list.add(new luoheBean(luoheBean.type_Inner));
            }
        }
        return list;
    }

    @Override
    protected Class<? extends BaseRecyclerViewAdapter<luoheBean>> onGetAdapterType() {
        return LuoheListAdapter.class;
    }

    @Override
    protected void onPullDown(AdapterManger<luoheBean> mAdapterManger) {
        mAdapterManger.initListToAdapter(createData());
        mHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyLoadComplete(0);
            }
        }, 1000);

    }

    Handler mHanlder = new Handler();

    @Override
    protected void onLoadMore(final AdapterManger<luoheBean> adapter) {

        mHanlder.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addListToAdapter(createData());
                notifyLoadComplete(0);
            }
        }, 1000);

    }

    public static class LuoheListAdapter extends BaseRecyclerViewAdapter<luoheBean> {


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
//            viewBundles.add(new ViewBundle(R.layout.luohe_adapter_item, ViewHolderItem.class));
//            viewBundles.add(new ViewBundle(R.layout.luohe_inner_item, VHInner.class));
        }

        @BindLayout(id = R.layout.luohe_adapter_item)
        static class ViewHolderItem extends BaseViewHolder<luoheBean> {
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


            public ViewHolderItem(View itemView) {
                super(itemView);
            }

            @Override
            protected void bindView(luoheBean bean, int position, final Context context) {

                commit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it
                                = new Intent(context, WriteThemeActivity.class);
                        context.startActivity(it);
                    }
                });

            }


        }

        @BindLayout(id = R.layout.luohe_inner_item)
        static class VHInner extends BaseViewHolder<luoheBean> {


            public VHInner(View itemView) {
                super(itemView);
            }

            @Override
            protected void bindView(luoheBean bean, int position, Context context) {

            }
        }

    }

    public static class luoheBean implements Serializable {
        public static final int type_luohe = 0;
        public static final int type_Inner = 1;

        public luoheBean(int type) {
            this.type = type;
        }

        public int type;

    }
}
