package com.luohe.android.luohe.mine;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.TitleViewPagerAdapter;
import com.luohe.android.luohe.luohe.LuoheListFragment;
import com.luohe.android.luohe.luohe.StyleGridViewAdapter;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.LuoheTagBean;
import com.luohe.android.luohe.utils.ToastUtil;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PersonStyleActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = PersonStyleActivity.class.getSimpleName();

    @Bind(R.id.activity_person_style)
    GridView personStyle;
    @Bind(R.id.tv_affirm)
    TextView tvAffirm;
    @Bind(R.id.host_person_style)
    GridView hostPersonStyle;

    List<LuoheTagBean> personStyles = null;
    String[] hostPersonStyles = null;
  //-------------选择完毕的风格----------------------------------------------
    String selectedHostStyle = null;//选择的主风格
    LinkedList<LuoheTagBean> myStyle = new LinkedList<>(); //已经选择的风格
    //-------------选择完毕的风格--------------------------------------------
    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("风格选择");
        getTitlebar().setDefauleBackBtn();
        initViews();
        getStryle();
    }

    private void initViews() {
        tvAffirm.setOnClickListener(this);
        personStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }
    private void initAdapter() {
        StyleGridViewAdapter adapter = new StyleGridViewAdapter(this, personStyles);
        personStyle.setAdapter(adapter);
        getMyStyle(adapter);
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_person_style;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_affirm:
                savePersonStyle();
                break;
        }
    }

    private void savePersonStyle() {
       /* if (!selectedHostStyle.isEmpty()&& myStyle!=null){

       }
        */
        //// TODO: 2016/6/4 设置-风格-修改
        ToastUtil.showToast("您选择的风格是"+myStyle.toString()+"主风格是"+selectedHostStyle);
    }

    public void getMyStyle(StyleGridViewAdapter adapter) {

        adapter.setCallBack(new StyleGridViewAdapter.CallBack() {
            @Override
            public void onSelect(LuoheTagBean luoheTagBean) {
                myStyle.add(luoheTagBean);
                hostPersonStyles = new String[myStyle.size()];
                for (int i = 0; i < myStyle.size(); i++) {
                    hostPersonStyles[i] = myStyle.get(i).baseName;
                }
                if (hostPersonStyles != null) {
                    MyHostStyleAdapter hostAdapter = new MyHostStyleAdapter(PersonStyleActivity.this, hostPersonStyles);
                    hostPersonStyle.setAdapter(hostAdapter);
                    hostAdapter.setCallBack(new MyHostStyleAdapter.CallBack() {
                        @Override
                        public void onSelect(String text) {
                            selectedHostStyle = text;
                        }

                        @Override
                        public void unSelect(String text) {
                        selectedHostStyle = "";
                        }
                    });
                }
            }

            @Override
            public void unSelect( LuoheTagBean luoheTagBean) {
              myStyle.remove(luoheTagBean);
                hostPersonStyles = new String[myStyle.size()];
                for (int i = 0; i < myStyle.size(); i++) {
                    hostPersonStyles[i] = myStyle.get(i).baseName;
                }
                if (hostPersonStyles != null) {
                    MyHostStyleAdapter hostAdapter = new MyHostStyleAdapter(PersonStyleActivity.this, hostPersonStyles);
                    hostPersonStyle.setAdapter(hostAdapter);
                }
            }
        });
    }

    private void getStryle() {
        ApiLoader.getApiService().lhlStyle().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CommonSubscriber<Result<List<LuoheTagBean>>>(this) {
                    @Override
                    public void onSuccess(Result<List<LuoheTagBean>> listResult) {

                        if (listResult != null && listResult.getResult() != null && listResult.getResult().size() > 0) {
                            personStyles = listResult.getResult();
                            Log.e(TAG, personStyles.toString());
                            initAdapter();
                            getLoadingHelper().showContentView();
                        } else {
                            getLoadingHelper().showNetworkError();
                        }
                    }

                }.onError(new CommonSubscriber.ErrorHandler() {
                    @Override
                    public void onError(Throwable e) {
                        getLoadingHelper().showNetworkError();
                    }
                }));
    }
}
