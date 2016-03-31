package com.luohe.android.luohe.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.common.StatuBarManager;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.view.swipeback.SwipeBackActivity;

import butterknife.ButterKnife;

/**
 * Created by GanQuan on 16/3/5.带标题栏的activity 默认带有侧滑的效果
 */
public abstract class AppCompatActivity extends SwipeBackActivity {
    TitleBar mTitlebar;
    LinearLayout mRootView;

    /**
     * not use setContentView int this method,please return id in{@link #onBindLayoutId}
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_layout);
        StatuBarManager.setTintStyle(this);
        mRootView = (LinearLayout) findViewById(R.id.root_view);
        mTitlebar = (TitleBar) findViewById(R.id.title_bar);
        int viewId = onBindLayoutId();
        View contentView = getLayoutInflater().inflate(viewId, null);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRootView.addView(contentView, lp);

        initBar(mTitlebar);
        butterKnifeBind();
        init(savedInstanceState);

    }

    /**
     * 初始化函数
     * @param savedInstanceState
     */
    protected abstract void init(Bundle savedInstanceState);


    /**
     * 绑定view和当前activity中的实例变量，请勿使用setcontent去pass一个layoutId
     *
     * @return Id for contentView
     */
    protected abstract int onBindLayoutId();

    private void initBar(TitleBar titleBar) {
//        titleBar.setImmersive(true);
        StatuBarManager.setImmersive(titleBar,mRootView);

        titleBar.setBackgroundColor(getResources().getColor(R.color.app_main_blue));

//        titleBar.setLeftImageResource(R.drawable.back_btn);
//        titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        //set the default click listener
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titleBar.setTitle("落和令");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);
        titleBar.setActionTextColor(Color.WHITE);

    }


    protected TitleBar getTitlebar() {
        return this.mTitlebar;
    }

    protected void hideTitlebar() {
        this.mTitlebar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
