package com.luohe.android.luohe.mine;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.AttenBean;
import com.luohe.android.luohe.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AttentionListActivity extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    @Bind(R.id.list_attention_listview)
    ListView listAttentionListView;
    List<AttenBean> attentionList = new ArrayList<>();
    AttentionListAdapter attentionListAdapter;
    @Override
    protected void init(Bundle savedInstanceState) {
        String title = getIntent().getStringExtra("title");
        int id = getIntent().getIntExtra("id", -1);
        getTitlebar().setTitle(title);
        getTitlebar().setDefauleBackBtn();
       initViews();
        if (id != -1) {
            getMyAttention(id);
        } else {
            ToastUtil.showToast("请选择类型");
        }

    }

    private void getMyAttention(int id) {
        ApiLoader.getApiService().attenList(1, id, 0).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonSubscriber<Result<List<AttenBean>>>(this) {
            @Override
            public void onSuccess(Result<List<AttenBean>> result) {
                if (result != null && result.getResult() != null) {
                    if (result.getResult().size() != 0) {
                        attentionList.removeAll(attentionList);
                        attentionList = result.getResult();
                        attentionListAdapter = new AttentionListAdapter(AttentionListActivity.this,attentionList);
                        listAttentionListView.setAdapter(attentionListAdapter);
                    }
                }
            }
        });
    }

    private void initViews() {
        listAttentionListView.setOnItemClickListener(this);

    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_attention_list;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,TACityFallActivity.class);
       intent.putExtra("userId",attentionList.get(position).userId);
        startActivity(intent);
    }
}
