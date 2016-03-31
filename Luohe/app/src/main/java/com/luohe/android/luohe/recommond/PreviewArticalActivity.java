package com.luohe.android.luohe.recommond;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.utils.share.ShareHelper;
import com.luohe.android.luohe.utils.share.ShareItem;
import com.luohe.android.luohe.utils.share.ShareUtils;
import com.luohe.android.luohe.view.AbsPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by GanQuan on 16/3/14.
 */
public class PreviewArticalActivity extends AppCompatActivity {
    @Bind(R.id.share_btn)
    LinearLayout share_btn;
    @Bind(R.id.comment_btn)
    LinearLayout comment_btn;
    @Bind(R.id.more_btn)
    LinearLayout more_btn;
    MorePop mMorePop;

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("查看文章");
        getTitlebar().setLeftImageResource(R.drawable.back_btn);
        getTitlebar().addAction(new TitleBar.ImageAction(R.drawable.icon_tab_redian) {
            @Override
            public void performAction(View view) {

            }
        });
        initBottomViews();
    }

    private void initBottomViews() {

        setBottomBtn(R.drawable.btn_share, "分享", share_btn);
        setBottomBtn(R.drawable.btn_comment, "评论", comment_btn);
        setBottomBtn(R.drawable.btn_more, "更多", more_btn);


    }

    public void setBottomBtn(int resId, String name, View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.icon);

        TextView textView = (TextView) view.findViewById(R.id.name);
        imageView.setImageResource(resId);
        textView.setText(name);

    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.priview_article_activity;
    }

    @OnClick({R.id.share_btn, R.id.comment_btn, R.id.more_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_btn:
                List<ShareItem> shares = new ArrayList<>();
                ShareHelper.preShareWindow(this, shares, new ShareHelper.ShareClickListener() {
                    @Override
                    public void onItemClick(int position, Object object) {
                        //todo 具体分享内容
                        ShareUtils.showShare(position + 2, "分享内容", "分享图片", "分享链接", "分享标题", null);
                    }
                });
                ShareHelper.showPopWindow(this, v);
                break;
            case R.id.comment_btn:

                break;
            case R.id.more_btn:
                if (mMorePop == null) {
                    mMorePop = new MorePop(this);
                }
                mMorePop.showUp(more_btn);

                break;
        }
    }

    public static class MorePop extends AbsPopWindow {

        public MorePop(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutId() {
            return R.layout.pop_pre_more;
        }
    }
}
