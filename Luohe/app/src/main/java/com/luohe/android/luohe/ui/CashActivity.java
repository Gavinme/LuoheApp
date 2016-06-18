package com.luohe.android.luohe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.mine.CashAffirmActivity;
import com.luohe.android.luohe.mine.CashRecordActivity;
import com.luohe.android.luohe.utils.ToastUtil;

import java.text.ParseException;

import butterknife.Bind;

/**
 * Author: GanQuan Date: 16/5/29 Email:ganquan3640@gmail.com
 */

public class CashActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.cash_et_true_name)
    EditText cashTrueName;
    @Bind(R.id.cash_et_idcard)
    EditText cashIdCard;
    @Bind(R.id.cash_et_alipay_account)
    EditText cashAlipayAccount;
    @Bind(R.id.cash_et_use_money)
    EditText cashUseMoney;
    @Bind(R.id.cash_tv_payment_method)
    TextView cashPaymentMethod;
    @Bind(R.id.cash_tv_submit_applications)
    TextView cashSubmit;

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("提现申请");
        getTitlebar().setDefauleBackBtn();
        getTitlebar().addAction(new TitleBar.TextAction("提现记录") {
            @Override
            public void performAction(View view) {
                startActivity(new Intent(CashActivity.this, CashRecordActivity.class));
            }

            @Override
            public int getBackground() {
                return 0;
            }
        });
        initViews();
    }

    private void initViews() {

        cashSubmit.setOnClickListener(this);
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_layout_cash;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cash_tv_submit_applications:
                submit();
                //startActivity(new Intent(this, CashAffirmActivity.class));

                break;
        }
    }

    private void submit() {
        String name = cashTrueName.getText().toString();
        String idCard = cashIdCard.getText().toString();
        String account = cashAlipayAccount.getText().toString();
        String alipyMoney =cashUseMoney.getText().toString();
        boolean a = infoIsEmpty(name,idCard,account,alipyMoney);
      //  ToastUtil.showToast(name+"2"+idCard+"3"+account+"4"+alipyMoney);
        if(a){
            Intent affirminfoIntent = new Intent(this,CashAffirmActivity.class);
            affirminfoIntent.putExtra("name",name);
            affirminfoIntent.putExtra("idCard",idCard);
            affirminfoIntent.putExtra("account",account);
            affirminfoIntent.putExtra("alipyMoney",alipyMoney);
          //  ToastUtil.showToast(name+idCard+account+alipyMoney);
            startActivity(affirminfoIntent);

        }

    }

    private boolean infoIsEmpty(String  name, String idCard,String account,String alipyMoney) {

                if (name.isEmpty()) {
                    ToastUtil.showToast("真实姓名不能为空");
                    return false;
                }else
                if (idCard.isEmpty()) {
                    ToastUtil.showToast("收款人身份证号不能为空");
                    return false;
                }else
                if (account.isEmpty()) {
                    ToastUtil.showToast("支付宝账号不能为空");
                    return false;
                }else
                if (alipyMoney.isEmpty()) {
                    ToastUtil.showToast("支付金额不能为空");
                    return false;
                }

        return true;
    }
}
