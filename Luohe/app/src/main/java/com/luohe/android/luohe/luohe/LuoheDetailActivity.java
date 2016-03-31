package com.luohe.android.luohe.luohe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BindLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by GanQuan on 16/3/29.
 */
public class LuoheDetailActivity extends AppCompatActivity {
    @Bind(R.id.avatar)
    ImageView avatar;
    @Bind(R.id.state)
    TextView state;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.divider)
    View divider;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.commit)
    TextView commit;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    ListAdapter mAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("主题列表");
        getTitlebar().setDefauleBackBtn();
        getTitlebar().setLeftText(getString(R.string.luohe));
        mAdapter = new ListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.initList(createData());
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LuoheDetailActivity.this, WriteThemeActivity.class));
            }
        });
    }

    private List<LuoHeInnerBean> createData() {
        List<LuoHeInnerBean> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new LuoHeInnerBean());
        }
        return list;
    }


    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_detail_luohe;
    }

    public static class ListAdapter extends BaseRecyclerViewAdapter<LuoHeInnerBean> {

        public ListAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindVHLayoutId(List<Class<?>> viewBundles) {
            viewBundles.add(VHInner.class);
        }
    }

    @BindLayout(id = R.layout.luohe_inner_item)
    static class VHInner extends BaseRecyclerViewAdapter.BaseViewHolder<LuoHeInnerBean> {


        public VHInner(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(LuoHeInnerBean bean, int position, Context context) {

        }
    }
}
