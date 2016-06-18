package com.luohe.android.luohe.user;


import android.os.Bundle;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;

public class RegistSetTextActivity extends AppCompatActivity {

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("落和令昵称");
        getTitlebar().setDefauleBackBtn();
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_nick_set;
    }
}
