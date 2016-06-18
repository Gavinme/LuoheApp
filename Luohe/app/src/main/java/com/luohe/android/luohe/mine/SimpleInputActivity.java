package com.luohe.android.luohe.mine;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;

import butterknife.Bind;

/**
 * Author: GanQuan Date: 16/5/26 Email:ganquan3640@gmail.com
 */

public abstract class SimpleInputActivity extends AppCompatActivity {
    @Bind(R.id.edit_input_key)
    EditText edit_input_key;

    @Override
    protected void init(Bundle savedInstanceState) {

        getTitlebar().setDefauleBackBtn();
        getTitlebar().addAction(new TitleBar.TextAction("保存") {
            @Override
            public void performAction(View view) {
                onSaveAction(getContext());
            }
        });
        getTitlebar().setTitle(onSetTitle());
    }

    protected abstract String onSetTitle();

    protected abstract void onSaveAction(Context context);

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_layout_simpleinput;
    }

}
