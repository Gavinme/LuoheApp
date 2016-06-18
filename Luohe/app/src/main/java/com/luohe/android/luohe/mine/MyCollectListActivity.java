package com.luohe.android.luohe.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.recommond.PreviewWenfengActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Xinlei on 16/6/15.
 */
public class MyCollectListActivity extends FragmentHoldActivity<MyCollectListActivity.MyCollectListFragment> {

    @Override
    protected Class<MyCollectListFragment> onGetFragmentClazz() {
        return MyCollectListFragment.class;
    }

    @Override
    protected void onSetArguments(MyCollectListFragment fragment) {

    }

    public static class MyCollectListFragment extends BaseRefreshListFragment<CollectBean> {
        @Override
        protected void init(View view) {
            super.init(view);
        }

        @Override
        protected void onInit(View view, RecyclerView recyclerView) {
            super.onInit(view, recyclerView);
            getTitleBar().setTitle("我的收藏");
            getTitleBar().setDefauleBackBtn();
            getLoadingHelper().showLoadingView();
            refreshData();
        }


        @Override
        protected void onListItemClick(MyCollectListActivity.CollectBean bean, View view, int adapterPosition) {
            switch (bean.sctype) {
                case 1:
                    startActivity(PreviewWenfengActivity.getStartIntent(getActivity(), bean.id));
                    break;
                case 2:
                    // startActivity(PreviewWenfengActivity.getStartIntent(getActivity(), bean.titleId));
                    break;
                case 3:
                    startActivity(PreviewWenfengActivity.getStartIntent(getActivity(), bean.id));
                    break;
                case 4:
                    //startActivity(PreviewWenfengActivity.getStartIntent(getActivity(), bean.titleId));
                    break;
                case 5:
                    //startActivity(PreviewWenfengActivity.getStartIntent(getActivity(), bean.titleId));
                    break;

            }
        }

        @Override
        protected void onPullDown(final AdapterManger<CollectBean> mAdapterManger) {
            ApiLoader.getApiService().myCollect(0).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CommonSubscriber<Result<List<CollectBean>>>(getActivity()) {
                        @Override
                        public void onSuccess(Result<List<CollectBean>> result) {
                            getLoadingHelper().showContentView();
                            if (result.getResult() != null) {
                                mAdapterManger.initListToAdapter(result.getResult());
                            } else {
                                getLoadingHelper().showNetworkError();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            getLoadingHelper().showNetworkError();
                        }
                    });
        }

        @Override
        protected void onLoadMore(AdapterManger<CollectBean> adapter) {
        }

        @Override
        protected Class<? extends BaseRecyclerViewAdapter<CollectBean>> onGetAdapterType() {
            return ShareListAdapter.class;
        }
    }

    public static class ShareListAdapter extends BaseRecyclerViewAdapter<CollectBean> {
        public ShareListAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
            viewBundles.add(ViewHolderRecommend.class);
        }
    }

    @BindLayout(id = R.layout.item_my_collect)
    static class ViewHolderRecommend extends BaseRecyclerViewAdapter.BaseViewHolder<CollectBean> {



        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.styleName)
        TextView styleName;
        @Bind(R.id.styleSubContent)
        TextView styleSubContent;
        @Bind(R.id.publish_times)
        TextView publishTimes;

        public ViewHolderRecommend(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(CollectBean bean, int position, Context context) {

            avatar.setImageResource(R.drawable.ic_launcher);
            name.setText(bean.userName);
            styleSubContent.setText(bean.styleSubContent);
            styleName.setText(bean.styleName);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            String sd = simpleDateFormat.format(new Date(Long.parseLong(String.valueOf(bean.publishTime))));
            publishTimes.setText(sd);

        }
    }

    public static class CollectBean implements Serializable {
        int id;//分享ID
        int sctype;//分享类型
        /*1: 落和令文章
        2：时观文章
        3：文风文章
        4：评论
        5：回复
        6：落和令*/

        //如果是文章/落和令/文风
        int styleArtId;//文风或文章ID
        String styleName;//标题
        String sourceName;//源自落和令或文风
        int userId;//创者ID
        String userName;//创者名称
        String publishTime;//发布时间
        String styleSubContent;//（300）	文章截取内容

    }
}
