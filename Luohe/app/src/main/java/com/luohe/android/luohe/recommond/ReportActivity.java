package com.luohe.android.luohe.recommond;

import android.os.Bundle;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;

/**
 * Created by GanQuan on 16/3/30.
 */
public class ReportActivity extends AppCompatActivity {
    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setDefauleBackBtn();
        getTitlebar().setTitle(getString(R.string.report));
        getTitlebar().addAction(new TitleBar.TextAction("发布") {
            @Override
            public void performAction(View view) {
                //todo
            }

            @Override
            public int getBackground() {
                return R.drawable.rect_white_frame;
            }
        });

    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_report_detail;
    }
}
