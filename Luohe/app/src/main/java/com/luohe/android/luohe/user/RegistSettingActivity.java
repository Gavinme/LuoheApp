package com.luohe.android.luohe.user;

import android.app.Notification;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.luohe.StyleGridViewAdapter;
import com.luohe.android.luohe.mine.NickNameSetActivity;
import com.luohe.android.luohe.mine.PersonStyleActivity;
import com.luohe.android.luohe.mine.SettingItemView;
import com.luohe.android.luohe.widget.cityselector.model.ProvinceModel;
import com.luohe.android.luohe.widget.cityselector.model.XmlParserHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.Bind;

public class RegistSettingActivity extends AppCompatActivity implements View.OnClickListener {
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
    OptionsPickerView proOptions;
    TimePickerView pvTime;

    @Override
    protected void init(Bundle savedInstanceState) {

        getTitlebar().setTitle("注册信息");
        getTitlebar().setDefauleBackBtn();
        tv_logout.setText("确认");
        initViews();
        initTimer();
        initCitys();
        initActions();
    }



    private void initActions() {
        tv_logout.setOnClickListener(this);
    }

    private void initCitys() {

        List<List<List<String>>> distrList = new ArrayList<>();
        List<List<String>> cityList = new ArrayList<>();
        List<String> proList = new ArrayList<>();
        proOptions = new OptionsPickerView(this);
        AssetManager asset = getAssets();
        InputStream input = null;
        List<ProvinceModel> provinceList = null;
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
                // birthday.setRight();setText(getTime(date));
            }
        });
    }

    private CharSequence getTitle(CharSequence str) {
        return Html.fromHtml(String.format("%s<font color='#fb528e'> * </font>", str));
    }

    private void initViews() {
        security.setLeftTextview(0,"安全和隐私");
        security.setOnClickListener(this);
        nickName.setLeftTextview(0, getTitle("落和令昵称"));
        nickName.setOnClickListener(this);
        avatar.setLeftTextview(0, getTitle("头像 "));

        avatar.setOnClickListener(this);
        realName.setLeftTextview(0, "真实姓名");
        realName.setOnClickListener(this);
        sex.setLeftTextview(0, getTitle("性别 "));
        sex.setOnClickListener(this);
        birthday.setLeftTextview(0, "生日");
        birthday.setOnClickListener(this);
        address.setLeftTextview(0, "所在地");
        address.setOnClickListener(this);
        personIntroduce.setLeftTextview(0, "个人介绍");
        personIntroduce.setOnClickListener(this);
        personStyle.setLeftTextview(0, getTitle("才者风格"));
        personStyle.setOnClickListener(this);
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_affirm:
                //将用户填写的用户信息上传服务器
                saveinfo();
                break;
            case R.id.person_style:
                startActivity(new Intent(RegistSettingActivity.this,PersonStyleActivity.class));
                break;

            case R.id.nick_name:
                startActivity(new Intent(RegistSettingActivity.this,NickNameSetActivity.class));
                break;




        }

    }

    private void saveinfo() {
    }
}
