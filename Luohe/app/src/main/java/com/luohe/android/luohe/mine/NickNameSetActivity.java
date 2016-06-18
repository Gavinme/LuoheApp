package com.luohe.android.luohe.mine;

import android.os.Bundle;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;

/**
 * Created by GanQuan on 16/3/20.
 */
public class NickNameSetActivity extends AppCompatActivity {
    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("落和昵称");
        getTitlebar().setDefauleBackBtn();
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_nick_set;
    }
}
