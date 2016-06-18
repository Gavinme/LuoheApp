package com.luohe.android.luohe.mine;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;

import butterknife.Bind;

public class CashAffirmActivity extends AppCompatActivity {
    @Bind(R.id.cash_affirm_tv_true_name)
    TextView cashAffirmName;
    @Bind(R.id.cash_affirm_tv_idcard)
    TextView cashAffirmIdCard;
    @Bind(R.id.cash_affirm_tv_account)
    TextView cashAffirmAccount;
    @Bind(R.id.cash_affirm_tv_money)
    TextView cashAffirmMoney;
    private String name = null;
    private String idCard = null;
    private String account = null;
    private String alipyMoney = null;
    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("提现申请");
        getTitlebar().setDefauleBackBtn();
        Intent affirmInfoIntent = getIntent();
        name = affirmInfoIntent.getStringExtra("name");
        idCard = affirmInfoIntent.getStringExtra("idCard");
        account = affirmInfoIntent.getStringExtra("account");
        alipyMoney = affirmInfoIntent.getStringExtra("alipyMoney");

        if (!name.isEmpty()&&!idCard.isEmpty()&&!account.isEmpty()&&!alipyMoney.isEmpty()){
            cashAffirmName.setText("");
            cashAffirmIdCard.setText("");
            cashAffirmAccount.setText("");
            cashAffirmMoney.setText("");
            cashAffirmName.setText(name);
            cashAffirmIdCard.setText(idCard);
            cashAffirmAccount.setText(account);
            cashAffirmMoney.setText(alipyMoney);
        }
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_layout_cash_affirm;
    }
}
