package com.luohe.android.luohe.mine;

import android.os.Bundle;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;

/**
 * Created by GanQuan on 16/3/20.
 */
public class AboutMeActivity extends AppCompatActivity {
    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("关于创者");
        getTitlebar().setLeftImageResource(R.drawable.back_btn);
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_about_me;
    }
}
