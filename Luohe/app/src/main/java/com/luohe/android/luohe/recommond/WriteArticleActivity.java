package com.luohe.android.luohe.recommond;

import android.os.Bundle;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;

/**
 * Created by GanQuan on 16/3/30.写文章
 */
public class WriteArticleActivity extends AppCompatActivity {
    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("文章");
        getTitlebar().setDefauleBackBtn();
        getTitlebar().addAction(new TitleBar.ImageAction(R.drawable.icon_save_article) {
            @Override
            public void performAction(View view) {

            }
        });
        getTitlebar().addAction(new TitleBar.TextAction("发布") {
            @Override
            public void performAction(View view) {

            }

            @Override
            public int getBackground() {

                return R.drawable.rect_white_frame;
            }
        });
    }

    @Override
    protected int onBindLayoutId() {
        return 0;
    }
}
