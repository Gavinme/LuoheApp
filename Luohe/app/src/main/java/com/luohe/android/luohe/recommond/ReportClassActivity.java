package com.luohe.android.luohe.recommond;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.view.stickyheadersrecyclerview.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by GanQuan on 16/3/30.
 */
public class ReportClassActivity extends AppCompatActivity {
    @Bind(R.id.recycler_view)
    RecyclerView mRecycleView;
    ListAdapter mAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("举报分类");
        getTitlebar().setDefauleBackBtn();
        mAdapter = new ListAdapter(this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addItemDecoration(new DividerDecoration(this));
        mAdapter.initList(onGetList());
    }

    private List<Item> onGetList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item("垃圾营销"));
        list.add(new Item("不实信息"));
        list.add(new Item("有害信息"));
        list.add(new Item("违法信息"));
        list.add(new Item("淫秽信息"));
        list.add(new Item("人身攻击"));
        list.add(new Item("抄袭我的内容"));
        return list;
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_report_class;
    }

    static class Item {
        public String name;

        public Item(String name) {
            this.name = name;
        }
    }

    static class ListAdapter extends BaseRecyclerViewAdapter<Item> {


        public ListAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> VhClazzList) {
            VhClazzList.add(VHClass.class);
        }
    }

    @BindLayout(id = R.layout.layout_string_item)
    static class VHClass extends BaseRecyclerViewAdapter.BaseViewHolder<Item> {
        public VHClass(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(Item bean, int position, Context context) {

        }
    }
}
