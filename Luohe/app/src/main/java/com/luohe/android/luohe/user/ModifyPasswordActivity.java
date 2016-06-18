package com.luohe.android.luohe.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;

import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.utils.ToastUtil;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ModifyPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.modify_tv_original_password)
    TextView tvOriginalPassword;
    @Bind(R.id.modify_tv_new_password)
    TextView tvNewPassword;
    @Bind(R.id.modify_tv_affirm_password)
    TextView tvAffirmPassword;
    @Bind(R.id.modify_tv_affirm)
    TextView tvAffirm;

    String originalPassword = null ;
    String newPassword = null;
    String affirmPassword = null;

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("修改密码");
        getTitlebar().setDefauleBackBtn();
        tvAffirm.setOnClickListener(this);

    }

    private void savePassword() {

        originalPassword = tvOriginalPassword.getText().toString();
        newPassword = tvNewPassword.getText().toString();
        affirmPassword = tvAffirmPassword.getText().toString();
        if (originalPassword.isEmpty()) {
            ToastUtil.showToast("请输入当前密码");
            return;
        }else
        if (newPassword.isEmpty()) {
            ToastUtil.showToast("请输入新密码");
            return;
        }else
        if (affirmPassword.isEmpty() || !affirmPassword.equals(newPassword)) {
            ToastUtil.showToast("请输入相同的新密码");
return;
        } else
        if(newPassword.equals(originalPassword)){
            ToastUtil.showToast("新密码不能与当前密码相同");
            return;
        }
        
       //发送网络请求，修改密码
        ApiLoader
                .getApiService()
                .changePwd(originalPassword,newPassword,affirmPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<Result>(this) {
                    @Override
                    public void onSuccess(Result result) {
                        if (result.isHasReturnValidCode()) {
                            LogUtils.d("fxl",result.toString());
                          ToastUtil.showToast("密码修改成功，请重新登录");
                            UserInfoUtil.getInstance().logout();
                            EventBusControl.postSticky(new Object(), EventBusControl.LOG_OUT);
                            startActivity(new Intent(ModifyPasswordActivity.this,LoginActivity.class));
                            finish();

                        }else{
                            LogUtils.d("fxl","错误代码"+result.getCode());
                            ToastUtil.showToast("修改失败，请稍后重试。");
                        }
                    }
                });
    }


    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_tv_affirm:
                savePassword();//发送请求
                
                break;
        }
    }
}
