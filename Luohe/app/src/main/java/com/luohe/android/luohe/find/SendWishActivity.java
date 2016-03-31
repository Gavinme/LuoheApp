package com.luohe.android.luohe.find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.view.swipeback.SwipeBackActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendWishActivity extends SwipeBackActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.right_button)
    TextView rightBtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onBindLayoutId());
        ButterKnife.bind(this);

        title.post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });

    }

    protected void init() {
        title.setText(R.string.send_wish_act);
        rightBtn.setText(R.string.send);
        rightBtn.setBackgroundResource(R.drawable.rect_yellow_round);
    }

    protected int onBindLayoutId() {
        return R.layout.activity_send_wish;
    }

    @OnClick({R.id.back, R.id.right_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.right_button:
                break;
        }
    }
}
