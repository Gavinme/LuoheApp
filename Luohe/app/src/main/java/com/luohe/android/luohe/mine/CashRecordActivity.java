package com.luohe.android.luohe.mine;


import android.content.Context;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.mobileim.fundamental.widget.refreshlist.PullToRefreshDummyHeadListView;
import com.alibaba.mobileim.fundamental.widget.refreshlist.PullToRefreshListView;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.listview.BaseListViewAdapter;
import com.luohe.android.luohe.net.model.CashMoneyBean;
import com.luohe.android.luohe.net.model.LuoheTimeBean;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;

public class CashRecordActivity extends AppCompatActivity {
    @Bind(R.id.cash_record_listview)
    ListView recordListView;

    @Override
    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle("现金明细");
        getTitlebar().setDefauleBackBtn();
        initViews();
    }

    private void initViews() {
         ListAdapter adapter =new ListAdapter(this);
        recordListView.setAdapter(adapter);
    }

    @Override
    protected int onBindLayoutId() {
        return R.layout.activity_cash_record;
    }
    static class ListAdapter extends BaseListViewAdapter<CashMoneyBean> {

        public ListAdapter(Context context) {
            super(context);
        }

        @Override
        protected void onBindViewHolder(List<ViewBundle> list) {
            list.add(new ViewBundle(R.layout.item_cash_record, ViewHolder.class));

        }
    }

    static class ViewHolder extends BaseListViewAdapter.BaseListViewHolder<CashMoneyBean> {
        @Bind(R.id.cash_record_time)
        TextView cashRecordTime;
        @Bind(R.id.cash_record_indent_number)
        TextView cashRecordIndentNumber;
        @Bind(R.id.cash_record_deal_source)
        TextView cashRecordDealSource;
        @Bind(R.id.cash_record_money)
        TextView cashRecordMoney;

        @Override
        protected void setView(CashMoneyBean bean, Context context) {
            cashRecordTime.setText(bean.dateTime);
            cashRecordIndentNumber.setText(bean.orderId);
            cashRecordDealSource.setText(bean.paymentSource);
            cashRecordMoney.setText(bean.money);
        }
    }
}
