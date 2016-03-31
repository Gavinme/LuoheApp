package com.luohe.android.luohe.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.luohe.android.luohe.R;

import butterknife.ButterKnife;

/**
 * Created by GanQuan on 16/2/20.
 */
public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected Context getContext() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void butterKnifeBind() {
        ButterKnife.bind(this, this.findViewById(android.R.id.content));
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }
}
