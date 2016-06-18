package com.luohe.android.luohe.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;

import butterknife.Bind;

/**
 * Author: GanQuan Date: 16/5/21 Email:ganquan3640@gmail.com
 */

public class FindPassWordActivity extends AppCompatActivity implements View.OnClickListener {
	@Bind(R.id.edit_input_phoneno)
	EditText editInputPhoneno;
	@Bind(R.id.edit_input_sms)
	EditText editInputSms;
	@Bind(R.id.tv_get_token)
	TextView tvGetToken;
	@Bind(R.id.edit_input_ps)
	EditText editInputPs;
	@Bind(R.id.commit)
	TextView commit;

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setTitle("找回密码");
		getTitlebar().setDefauleBackBtn();
		commit.setOnClickListener(this);
		tvGetToken.setOnClickListener(this);
	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.activity_find_pw;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_get_token:

			break;
		case R.id.commit:
			break;
		}

	}
}
