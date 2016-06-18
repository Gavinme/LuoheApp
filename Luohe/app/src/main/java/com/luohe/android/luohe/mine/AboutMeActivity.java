package com.luohe.android.luohe.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.common.listview.BaseListViewAdapter;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.user.UserCommonInfo;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.ImageUtils;
import com.luohe.android.luohe.utils.ToastUtil;
import com.luohe.android.luohe.widget.widget.FlowLayout;
import com.luohe.android.luohe.widget.widget.TagListView;
import com.luohe.android.luohe.widget.widget.TagView;

import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/3/20.
 */
public class AboutMeActivity extends AppCompatActivity implements View.OnClickListener {
    private int uid = -1;

    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.address_time)
    TextView addressTime;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.tv_count_attention)
    TextView tvCountAttention;
    @Bind(R.id.tv_count_fan)
    TextView tvCountFan;
    @Bind(R.id.tv_caizhi_rank)
    TextView tvCaizhiRank;
    @Bind(R.id.tv_count_caizhi)
    TextView tvCountCaizhi;
    @Bind(R.id.tv_count_caizhi_now)
    TextView tvCountCaizhiNow;
    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;
    @Bind(R.id.tagview)
    GridView tagview;
    @Bind(R.id.linear_attention)  //关于创者-关注
            LinearLayout linearAttention;
    @Bind(R.id.linear_fans)
    LinearLayout linearFans;  //关于创者-粉丝


    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("关于创者");
        getTitlebar().setLeftImageResource(R.drawable.back_btn);
            uid = getIntent().getIntExtra("uid",-1);
            loadData(uid);
        linearAttention.setOnClickListener(this); //进入我的关注界面
        linearFans.setOnClickListener(this); //进入粉丝界面

    }

    private void loadData( int uid) {
        getLoadingHelper().showLoadingView();
        ApiLoader.getApiService().userInfo(uid)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<Result<UserCommonInfo>>(this) {
                    @Override
                    public void onSuccess(Result<UserCommonInfo> result) {
                        getLoadingHelper().showContentView();
                        if (result.isHasReturnValidCode() && result.getResult() != null) {
                            ImageUtils.displayRoundImage(result.getResult().headUrl, avatar);
                            name.setText(result.getResult().nickName);
                            addressTime.setText(result.getResult().province);
                            desc.setText(result.getResult().accountDesc);
                            tvCountAttention.setText(result.getResult().attentionCount + "");
                            tvCountFan.setText(result.getResult().fansCount + "");
                            tvCountCaizhiNow.setText(String.format("现有才值：%1s", result.getResult().nowValue));
                            tvCountCaizhi.setText(String.format("累计才值：%1s", result.getResult().allValue));
                            tvCaizhiRank.setText(String.format("累计才值排名：第%1s位", result.getResult().allRank));
                            GridTagAdapter gridTagAdapter = new GridTagAdapter(getContext());
                            tagview.setAdapter(gridTagAdapter);
                            gridTagAdapter.initList(result.getResult().userStyle);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_attention:
                Intent attentionIntent = new Intent(this, AttentionListActivity.class);
                attentionIntent.putExtra("title", "我的关注");
                attentionIntent.putExtra("id", 1);
                startActivity(attentionIntent);
                break;
            case R.id.linear_fans:
                Intent fansIntent = new Intent(this, AttentionListActivity.class);
                fansIntent.putExtra("title", "我的粉丝");
                fansIntent.putExtra("id",2);
                startActivity(fansIntent);
                break;


        }
    }

    static class GridTagAdapter extends BaseListViewAdapter<UserCommonInfo.UserStyle> {
        public GridTagAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindViewHolder(List<ViewBundle> list) {
            list.add(new ViewBundle(R.layout.itme_tag, ViewHolder.class));

        }
    }

    static class ViewHolder extends BaseListViewAdapter.BaseListViewHolder<UserCommonInfo.UserStyle> {
        @Bind(R.id.tv_main)
        TextView tv_main;
        @Bind(R.id.tv_tag)
        TextView tv_tag;

        @Override
        protected void setView(UserCommonInfo.UserStyle bean, Context context) {
            if (bean.isMainStyle == 1) {
                tv_main.setVisibility(View.VISIBLE);

            } else {
                tv_main.setVisibility(View.GONE);
            }
            tv_tag.setText(bean.baseName);
        }
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_about_me;
    }

}
