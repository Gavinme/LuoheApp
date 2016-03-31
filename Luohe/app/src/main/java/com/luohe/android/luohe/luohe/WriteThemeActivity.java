package com.luohe.android.luohe.luohe;

import android.os.Bundle;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;

/**
 * Created by GanQuan on 16/3/13.
 */
public class WriteThemeActivity extends AppCompatActivity {
    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("写主题");
        getTitlebar().setLeftImageResource(R.drawable.back_btn);
        getTitlebar().addAction(new TitleBar.TextAction("发布") {
            @Override
            public void performAction(View view) {

            }

        });
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.write_theme_activity;
    }
}
