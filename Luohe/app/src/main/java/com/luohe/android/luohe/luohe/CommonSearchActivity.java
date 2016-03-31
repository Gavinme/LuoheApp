package com.luohe.android.luohe.luohe;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.common.TitleBar;

import butterknife.Bind;

/**
 * Created by GanQuan on 16/3/9.
 */
public class CommonSearchActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btnLuohe;
    LinearLayout btnUser;
    LinearLayout btnArticle;

    @Bind(R.id.activity_content)
    FrameLayout activity_content;

    @Override
    protected void init(Bundle savedInstanceState) {
        View searchItemsView = getLayoutInflater().inflate(R.layout.search_init_view, null);
        activity_content.addView(searchItemsView);
        btnLuohe = (LinearLayout) searchItemsView.findViewById(R.id.luohe);
        btnUser = (LinearLayout) searchItemsView.findViewById(R.id.user);
        btnArticle = (LinearLayout) searchItemsView.findViewById(R.id.article);
        btnArticle.setOnClickListener(this);
        btnLuohe.setOnClickListener(this);
        btnUser.setOnClickListener(this);
        initBtns();
        initTitleBar();


    }

    private void initBtns() {
        initBtn(btnLuohe, R.drawable.search_icon_luohe, "落和");
        initBtn(btnArticle, R.drawable.search_icon_article, "文章");
        initBtn(btnUser, R.drawable.search_icon_user, "用户");
    }

    private void initBtn(View btn, int resId, String text) {
        ImageView imageView = (ImageView) btn.findViewById(R.id.image);
        imageView.setImageResource(resId);
        TextView textView = (TextView) btn.findViewById(R.id.text);
        textView.setText(text);
    }

    private void initTitleBar() {
        TitleBar titleBar = getTitlebar();
        View customView = getLayoutInflater().inflate(R.layout.search_view, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleBar.setCustomTitle(customView, lp);
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.container_activity;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.luohe:
                break;
            case R.id.user:
                break;
            case R.id.article:
                break;
        }
    }
}
