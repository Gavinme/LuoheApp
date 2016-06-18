package com.luohe.android.luohe.find;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.luohe.FragmentAdapter;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.LuoheTagBean;
import com.luohe.android.luohe.user.UserCommonInfo;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.ToastUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FindRankCategoryFragment extends BaseFragment {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.iv_my_avatar) //排行榜，我的头像
    ImageView ivMyAvatar;
    @Bind(R.id.tv_my_rank)
    TextView tvMyRank;

    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected void init(View view) {
        ImageLoader.getInstance().displayImage(UserInfoUtil.getInstance().getUserInfo().getComUserInfo().headUrl, ivMyAvatar);
        if (!TextUtils.isEmpty(UserInfoUtil.getInstance().getUserInfo().getComUserInfo().allRank+"")) {
            tvMyRank.setText("我的排名:" + UserInfoUtil.getInstance().getUserInfo().getComUserInfo().allRank);
        }else{
            LogUtils.d("luohe","用户基本信息"+UserInfoUtil.getInstance().getUserInfo().toString());
        }
        if (getArguments().getInt(FindRankFragment.CATEGORY, 0) == 2) {
            initViewPagers(new ArrayList<LuoheTagBean>());
        } else
            loadTags();
    }


    private void loadTags() {
        initLoadingHelper(viewPager).showLoadingView();
        getLoadingHelper().addRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTags();
            }
        });
        ApiLoader.getApiService().lhlStyle().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<Result<List<LuoheTagBean>>>(getActivity()) {
                    @Override
                    public void onSuccess(Result<List<LuoheTagBean>> listResult) {

                        if (listResult != null && listResult.getResult() != null && listResult.getResult().size() > 0) {
                            List<LuoheTagBean> list = listResult.getResult();
                            initViewPagers(list);
                            getLoadingHelper().showContentView();
                        } else {
                            getLoadingHelper().showNetworkError();
                        }

                    }

                }.onError(new CommonSubscriber.ErrorHandler() {
                    @Override
                    public void onError(Throwable e) {
                        getLoadingHelper().showNetworkError();
                    }
                }));
    }

    private void initViewPagers(List<LuoheTagBean> tagList) {
        List<String> list = new ArrayList<>();
        for (LuoheTagBean bean : tagList) {
            list.add(bean.baseName);
            Bundle bundle = new Bundle();
            bundle.putInt(FindRankFragment.CATEGORY, getArguments().getInt(FindRankFragment.CATEGORY, 0));
            bundle.putInt(FindRankFragment.TYPE, bean.baseId);
            fragments.add(Fragment.instantiate(getActivity(), FindRankFragment.class.getName(), bundle));
        }
        if (tagList.isEmpty()) {
            list.add("好友");
            Bundle bundle = new Bundle();
            bundle.putInt(FindRankFragment.CATEGORY, getArguments().getInt(FindRankFragment.CATEGORY, 0));
            fragments.add(Fragment.instantiate(getActivity(), FindRankFragment.class.getName(), bundle));
            tabLayout.setVisibility(View.GONE);
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), fragments, list);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(list.size() - 1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ((FindRankFragment) fragments.get(position)).getData();
            }
        });
    }

    public void getData() {
        if (!fragments.isEmpty())
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((FindRankFragment) fragments.get(viewPager.getCurrentItem())).getData();
                }
            }, 200);
    }

    protected int onBindLayoutId() {
        return R.layout.fragment_find_rank_category;
    }

}
