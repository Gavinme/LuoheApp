package com.luohe.android.luohe.ui;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.base.FragmentHoldActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GanQuan on 16/3/27.
 */
public class MyWenFengListActivity extends FragmentHoldActivity<MyWenFengListActivity.MyWenFengListFragment> {


    @Override
    protected Class<MyWenFengListFragment> onGetFragmentClazz() {
        return MyWenFengListFragment.class;
    }

    @Override
    protected void onSetArguments(MyWenFengListFragment fragment) {

    }

    public static class MyWenFengListFragment extends BaseRefreshListFragment<WenFengBean> {
        Handler mHanlder = new Handler();


        private List<WenFengBean> createData() {
            List<WenFengBean> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new WenFengBean());
            }
            return list;
        }

        @Override
        protected void onPullDown(AdapterManger<WenFengBean> mAdapterManger) {
            mHanlder.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshData();
                }
            }, 1000);
        }

        @Override
        protected void onLoadMore(AdapterManger<WenFengBean> adapter) {
            mHanlder.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notifyLoadComplete(0);
                }
            }, 1000);
        }

        @Override
        protected Class<? extends BaseRecyclerViewAdapter<WenFengBean>> onGetAdapterType() {
            return WenFengListAdapter.class;
        }
    }

    public static class WenFengListAdapter extends BaseRecyclerViewAdapter<WenFengBean> {
        public WenFengListAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
            viewBundles.add(WenFengBean.class);
//            viewBundles.add(new ViewBundle(R.layout.recommen_list_item,. class));
        }
    }

    @BindLayout(id = R.layout.recommen_list_item)
    static class ViewHolderRecommend extends BaseRecyclerViewAdapter.BaseViewHolder<WenFengBean> {


        public ViewHolderRecommend(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(WenFengBean bean, int position, Context context) {

        }
    }

    public static class WenFengBean implements Serializable {
    }
}
