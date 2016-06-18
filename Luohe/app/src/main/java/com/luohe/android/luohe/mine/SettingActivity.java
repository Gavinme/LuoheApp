package com.luohe.android.luohe.mine;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.eventbus.EventBusControl;
import com.luohe.android.luohe.net.data.ApiParams;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.user.UserCommonInfo;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.ToastUtil;
import com.luohe.android.luohe.widget.cityselector.model.ProvinceModel;
import com.luohe.android.luohe.widget.cityselector.model.XmlParserHandler;

import org.simple.eventbus.Subscriber;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/3/20.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

	@Bind(R.id.security)
	SettingItemView security;
	@Bind(R.id.nick_name)
	SettingItemView nickName;
	@Bind(R.id.avatar)
	SettingItemView avatar;
	@Bind(R.id.real_name)
	SettingItemView realName;
	@Bind(R.id.sex)
	SettingItemView sex;
	@Bind(R.id.birthday)
	SettingItemView birthday;
	@Bind(R.id.address)
	SettingItemView address;
	@Bind(R.id.person_introduce)
	SettingItemView personIntroduce;
	@Bind(R.id.person_style)
	SettingItemView personStyle;
	@Bind(R.id.tv_logout)
	TextView tv_logout;
	OptionsPickerView proOptions;// address
	TimePickerView pvTime;// 生日

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setTitle("设置");
		getTitlebar().setDefauleBackBtn();

		if (!UserInfoUtil.getInstance().IsLogin()) {
			getLoadingHelper().showNetworkError();
			return;
		}
		initViews(UserInfoUtil.getInstance().getUserInfo().getComUserInfo());
		initTimer();
		initCitys();
		initActions();
	}

	private void initActions() {
		tv_logout.setOnClickListener(this);

	}

	private void initTimer() {
		pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
		// 控制时间范围
		// Calendar calendar = Calendar.getInstance();
		// pvTime.setRange(calendar.get(Calendar.YEAR) - 20,
		// calendar.get(Calendar.YEAR));
		pvTime.setTime(new Date());
		pvTime.setCyclic(false);
		pvTime.setCancelable(true);
		// 时间选择后回调
		pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                ToastUtil.showToast(date.toString());
                String time = dateFormat.format(date);
                //birthday.setRightText(DateFormat.getDateInstance().format(time));
                birthday.setRightText(time);
                updateNetBir(time);
            }
        });
    }

	private void updateNetBir(String s) {
		UserRequest info = new UserRequest();
		info.birthday = s;
		ApiParams apiParams = ParmarsUtils.getParamas(info);
		ApiLoader.getApiService().updateUserInfo(apiParams).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonSubscriber<Result>(this) {
					@Override
					public void onSuccess(Result result) {
						if (result.isHasReturnValidCode()) {
							ToastUtil.showToast("更新成功");
						} else {
							ToastUtil.showToast("失败");
						}
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						ToastUtil.showToast("失败");
					}
				});
	}

	List<ProvinceModel> provinceList = null;

	private void initCitys() {
		final List<List<List<String>>> distrList = new ArrayList<>();
		final List<List<String>> cityList = new ArrayList<>();
		final List<String> proList = new ArrayList<>();
		proOptions = new OptionsPickerView(this);
		AssetManager asset = getAssets();
		InputStream input = null;

		try {
			input = asset.open("province_data.xml");

			// 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		if (provinceList != null && provinceList.size() > 0) {
			for (ProvinceModel list : provinceList) {
				proList.add(list.getPickerViewText());
				cityList.add(list.getCityNameList());
				distrList.add(list.getDisNameList());
			}
		}
		proOptions.setPicker(proList, cityList, distrList, true);
		proOptions.setCyclic(false);
		proOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				String addName = provinceList.get(options1).getName()
						+ provinceList.get(options1).getCityList().get(option2).getName()
						+ provinceList.get(options1).getCityList().get(option2).getDisList().get(options3);
				address.setRightText(addName);
				updateNetAddress(addName);
			}
		});

	}

	private void updateNetAddress(final String addName) {
		UserRequest userRequest = new UserRequest();
		userRequest.city = addName;
		ApiLoader.getApiService().updateUserInfo(ParmarsUtils.getParamas(userRequest)).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonSubscriber<Result>(this) {
					@Override
					public void onSuccess(Result result) {
						if (result.isHasReturnValidCode()) {
							ToastUtil.showToast("设置成功");
						} else {
							ToastUtil.showToast("设置失败");
						}
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						ToastUtil.showToast("设置失败");
					}
				});

	}

	private void initViews(UserCommonInfo info) {
		security.setLeftTextview(0, "安全和隐私");
		security.setOnClickListener(this);
		nickName.setLeftTextview(0, "落和令昵称");
		nickName.setRightText(info.nickName);
		nickName.setOnClickListener(this);
		avatar.setLeftTextview(0, "头像");
		avatar.setOnClickListener(this);
		realName.setLeftTextview(0, "真实姓名");
		realName.setRightText(UserInfoUtil.getInstance().getUserInfo().getTrueName());
		realName.setOnClickListener(this);
		realName.setRightText(UserInfoUtil.getInstance().getUserInfo().getTrueName());
		sex.setLeftTextview(0, "性别");
		sex.setRightText(info.sex.equals("s") ? "男" : "女");
		sex.setOnClickListener(this);
		sex.setRightBowVisiable(View.INVISIBLE);
		birthday.setLeftTextview(0, "生日");
		birthday.setRightText(UserInfoUtil.getInstance().getUserInfo().getComUserInfo().birthday);
		birthday.setOnClickListener(this);
		address.setLeftTextview(0, "所在地");
		address.setRightText(info.province);
		address.setOnClickListener(this);
		personIntroduce.setLeftTextview(0, "个人介绍");
		personIntroduce.setRightText(info.accountDesc);
		personIntroduce.setOnClickListener(this);
		personStyle.setLeftTextview(0, "创者风格");
		personStyle.setOnClickListener(this);

	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.activity_setting;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.security:
			/*
			 * Intent jumpSecurityIntent = new Intent(this,
			 * SecurityActivity.class);
			 * jumpSecurityIntent.putExtra("luohe_account"
			 * ,UserInfoUtil.getInstance().getUserInfo().getAccount());
			 * startActivity(jumpSecurityIntent); finish();
			 */
			startActivity(new Intent(this, SecurityActivity.class));
			break;

		case R.id.nick_name:
			startActivity(UserInfoSettingActivity.getStartIntent(this, 3, "落和昵称"));
			break;
		case R.id.avatar:
			startActivity(new Intent(this, AvatarSetActivity.class));
			break;
		case R.id.real_name:
			startActivity(UserInfoSettingActivity.getStartIntent(this, 1, "真实姓名"));
			break;
		case R.id.sex:

			break;
		case R.id.birthday:
			pvTime.show();
			break;
		case R.id.address:
			proOptions.show();
			break;
		case R.id.person_introduce:
			startActivity(UserInfoSettingActivity.getStartIntent(this, 2, "个人介绍"));
			break;
		case R.id.person_style:
			startActivity(new Intent(this, PersonStyleActivity.class));
			break;
		case R.id.tv_logout:
			logOut();
			break;
		}
	}

	public void setContent(UserRequest userRequest) {
	}

	@Subscriber(tag = "set_true_name")
	public void setTrueName(String s) {
		realName.setRightText(s);
	}

	@Subscriber(tag = "set_account_desc")
	public void setAccountDesc(String s) {
		personIntroduce.setRightText(s);
	}

	@Subscriber(tag = "set_nikename")
	public void setNickName(String s) {
		nickName.setRightText(s);
	}

	private void logOut() {
		UserInfoUtil.getInstance().logout();
		EventBusControl.postSticky(new Object(), EventBusControl.LOG_OUT);
		finish();
	}

}
