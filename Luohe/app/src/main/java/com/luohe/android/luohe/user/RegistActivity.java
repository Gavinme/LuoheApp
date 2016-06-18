package com.luohe.android.luohe.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/4/20.
 */
public class RegistActivity extends AppCompatActivity {
    @Bind(R.id.edit_input_phoneno)
    EditText editInputPhoneno;
    @Bind(R.id.edit_input_ps)
    EditText editInputPs;
    @Bind(R.id.edit_input_sms)
    EditText editInputSms;
    @Bind(R.id.tv_get_token)
    TextView tvGetToken;
    @Bind(R.id.commit)
    TextView commit;
    @Bind(R.id.regist_Invitation)
    TextView Invitation;

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("注册");
        getTitlebar().setDefauleBackBtn();
        getTitlebar().setLeftText("登录");

    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_regist;
    }

    @OnClick({R.id.tv_get_token, R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_token:
                onGetSmsToken();
                break;
            case R.id.commit:
                registUser();
                // startActivity(new Intent(this,RegistSettingActivity.class));
                break;
        }
    }

    private void registUser() {
        if (isStrEmpty(editInputPhoneno.getText().toString()) || editInputPhoneno.getText().toString().length() != 11) {
            ToastUtil.showToast("手机号码格式不正确");
            return;
        } else if (isStrEmpty(editInputPs.getText().toString())) {
            ToastUtil.showToast("密码不能为空");
            return;
        } else if (isStrEmpty(editInputSms.getText().toString())) {
            ToastUtil.showToast("短信验证码不能为空");
            return;
        }
        ApiLoader
                .getApiService()
                .regUserOne(editInputPhoneno.getText().toString(), editInputPs.getText().toString(),
                        editInputSms.getText().toString(), 1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonSubscriber<Result>(this) {
            @Override
            public void onSuccess(Result result) {
                if (result.isHasReturnValidCode()) {
                    startActivity(new Intent(RegistActivity.this, RegistSettingActivity.class));

                } else {
                    LogUtils.d("fxl", "错误代码" + result.getCode());
                    ToastUtil.showToast("注册失败，请稍后重试。");
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }

        });

    }

    private void onGetSmsToken() {

        if (TextUtils.isEmpty(editInputPhoneno.getText().toString())
                || editInputPhoneno.getText().toString().length() != 11) {
            ToastUtil.showToast("手机号码格式不正确");
            return;
        }
        //tvGetToken.setText("获取中...");
        ApiLoader.getApiService().mobileValidate(editInputPhoneno.getText().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonSubscriber<Result>(this) {
            @Override
            public void onSuccess(Result result) {
                if (result.isHasReturnValidCode()) {
                    ToastUtil.showToast("验证码发送成功");

                    handler.postDelayed(runnable, 1000);
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        });
    }

    int recLen = 60;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen > 0) {
                recLen--;
                tvGetToken.setText("" + recLen);
                tvGetToken.setClickable(false);
                handler.postDelayed(this, 1000);

            } else {
                recLen = 60;
                tvGetToken.setText("获取验证码");
                tvGetToken.setClickable(true);
            }
        }

    };

}
