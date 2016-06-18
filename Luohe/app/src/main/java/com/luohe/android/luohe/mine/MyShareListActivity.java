package com.luohe.android.luohe.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.base.FragmentHoldActivity;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.recommond.PreviewArticalActivity;
import com.luohe.android.luohe.recommond.PreviewWenfengActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by XinLei on 16/6/14.
 */
public class MyShareListActivity extends FragmentHoldActivity<MyShareListActivity.MyShareListFragment> {

    @Override
    protected Class<MyShareListFragment> onGetFragmentClazz() {
        return MyShareListFragment.class;
    }

    @Override
    protected void onSetArguments(MyShareListFragment fragment) {

    }

    public static class MyShareListFragment extends BaseRefreshListFragment<ShareBean> {
        @Override
        protected void init(View view) {
            super.init(view);
        }

        @Override
        protected void onInit(View view, RecyclerView recyclerView) {
            super.onInit(view, recyclerView);
            getTitleBar().setTitle("我的分享");
            getLoadingHelper().showLoadingView();
            refreshData();
        }


        @Override
        protected void onListItemClick(MyShareListActivity.ShareBean bean, View view, int adapterPosition) {
            switch (bean.shareType) {
                case 1:
                    startActivity(PreviewArticalActivity.getStartIntent(getActivity(), bean.titleId));
                    break;
                case 2:
                   // startActivity(PreviewWenfengActivity.getStartIntent(getActivity(), bean.titleId));
                    break;
                case 3:
                   // startActivity(PreviewArticalActivity.getStartIntent(getActivity(), bean.titleId));
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
        protected void onPullDown(final AdapterManger<ShareBean> mAdapterManger) {
            ApiLoader.getApiService().myShare(0).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new CommonSubscriber<Result<List<ShareBean>>>(getActivity()) {
                        @Override
                        public void onSuccess(Result<List<ShareBean>> result) {
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
        protected void onLoadMore(AdapterManger<ShareBean> adapter) {
        }

        @Override
        protected Class<? extends BaseRecyclerViewAdapter<ShareBean>> onGetAdapterType() {
            return ShareListAdapter.class;
        }
    }

    public static class ShareListAdapter extends BaseRecyclerViewAdapter<ShareBean> {
        public ShareListAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
            viewBundles.add(ViewHolderRecommend.class);
        }
    }

    @BindLayout(id = R.layout.item_my_share)
    static class ViewHolderRecommend extends BaseRecyclerViewAdapter.BaseViewHolder<ShareBean> {

        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.shareContent)
        TextView shareContent;
        @Bind(R.id.styleName)
        TextView styleName;
        @Bind(R.id.publish_times)
        TextView publishTimes;

        public ViewHolderRecommend(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(ShareBean bean, int position, Context context) {

            loadRoundImagUrl(avatar, bean.headUrl);
            name.setText(bean.userName);
            shareContent.setText(bean.shareContent);
            styleName.setText(bean.styleName);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
            String sd = simpleDateFormat.format(new Date(Long.parseLong(String.valueOf(bean.publishTime))));
            publishTimes.setText(sd);

        }
    }

    public static class ShareBean implements Serializable {

        private int id;
        private int shareType;
        private int titleId;
        private int styleRank;
        private String shareContent;
        private String headUrl;
        //如果是文章
        private int styleArtId;//	文风或文章ID
        private String styleName;//标题
        private String sourceName;//源自落和令或文风
        private int userId;//创者ID
        private String userName;//创者名称
        private String publishTime;//发布时间
        private String styleSubContent;//（300）	文章截取内容
        // 如果是分享评论
        private String comment;//评论内容
        private int praiseNum;//赞

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShareType() {
            return shareType;
        }

        public void setShareType(int shareType) {
            this.shareType = shareType;
        }

        public int getTitleId() {
            return titleId;
        }

        public void setTitleId(int titleId) {
            this.titleId = titleId;
        }

        public int getStyleRank() {
            return styleRank;
        }

        public void setStyleRank(int styleRank) {
            this.styleRank = styleRank;
        }

        public String getShareContent() {
            return shareContent;
        }

        public void setShareContent(String shareContent) {
            this.shareContent = shareContent;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getStyleArtId() {
            return styleArtId;
        }

        public void setStyleArtId(int styleArtId) {
            this.styleArtId = styleArtId;
        }

        public String getStyleName() {
            return styleName;
        }

        public void setStyleName(String styleName) {
            this.styleName = styleName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getStyleSubContent() {
            return styleSubContent;
        }

        public void setStyleSubContent(String styleSubContent) {
            this.styleSubContent = styleSubContent;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getPraiseNum() {
            return praiseNum;
        }

        public void setPraiseNum(int praiseNum) {
            this.praiseNum = praiseNum;
        }
    }
}
