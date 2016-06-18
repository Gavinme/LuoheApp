package com.luohe.android.luohe.mine;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.utils.ToastUtil;

import org.w3c.dom.Text;

/**
 * Created by XinLe on 2016/5/31.选择主风格
 */
public class MyHostStyleAdapter extends BaseAdapter {
    private Context context;
    private String[] hostPersonStyles = {"2", "1"};
    private LayoutInflater inflater;

    int count = 0;
    CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public MyHostStyleAdapter(Context context, String[] hostPersonStyles) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.hostPersonStyles = hostPersonStyles;

    }

    @Override
    public int getCount() {
        return hostPersonStyles.length;
    }

    @Override
    public Object getItem(int position) {
        return hostPersonStyles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview_host_person_style, parent, false);
            vh = new ViewHolder();
            vh.tvHostStyle = (TextView) convertView.findViewById(R.id.tv_host_style);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvHostStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    if (count < 1) {
                        v.setSelected(true);

                        v.setBackgroundResource(R.drawable.rect_pink);
                        count++;
                        if (callBack != null) {
                            callBack.onSelect(((TextView) v).getText().toString());

                        }
                    } else {
                   ToastUtil.showToast("只能选择一个主风格");

                    }
                } else {
                    count--;
                    v.setSelected(false);

                    v.setBackgroundResource(R.drawable.rect_blue);
                    if (callBack != null) {
                        callBack.unSelect(((TextView) v).getText().toString());
                    }

                }
            }
        });
        vh.tvHostStyle.setBackgroundResource(R.drawable.rect_blue);
        vh.tvHostStyle.setText(hostPersonStyles[position]);
        return convertView;
    }

    class ViewHolder {
        TextView tvHostStyle;
    }

    interface CallBack {
        void onSelect(String text);

        void unSelect(String text);
    }
}
