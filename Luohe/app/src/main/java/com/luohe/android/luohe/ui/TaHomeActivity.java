package com.luohe.android.luohe.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.find.FindItemView;

import butterknife.Bind;

/**
 * Author: GanQuan Date: 16/5/29 Email:ganquan3640@gmail.com
 */

public class TaHomeActivity extends AppCompatActivity {
    String TAG = MineTabFragment.class.getSimpleName();
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.address_time)
    TextView addressTime;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.about_me)
    FindItemView aboutMe;
    @Bind(R.id.luohe)
    FindItemView luohe;
    @Bind(R.id.theme)
    FindItemView theme;
    @Bind(R.id.wenfeng)
    FindItemView wenfeng;
    @Bind(R.id.share)
    FindItemView share;
    @Bind(R.id.collect)
    FindItemView collect;
    @Bind(R.id.setting)
    FindItemView setting;

    @Bind(R.id.container)
    ScrollView container;
    @Override
	protected void init(Bundle savedInstanceState) {

	}

	@Override
	protected int onBindLayoutId() {
		return 0;
	}
}
