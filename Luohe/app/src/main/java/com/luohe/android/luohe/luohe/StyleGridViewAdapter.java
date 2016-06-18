package com.luohe.android.luohe.luohe;

import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.mine.PersonStyleActivity;
import com.luohe.android.luohe.net.model.LuoheTagBean;
import com.luohe.android.luohe.utils.ToastUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by XinLe on 2016/5/27. 风格
 */
public class StyleGridViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;

    private List<LuoheTagBean> personStyles = null;

    private int count = 0;
    CallBack callBack;//将选中的数据回传到Activity

    public StyleGridViewAdapter(Context context, List<LuoheTagBean> personStyles) {
        this.context = context;
        this.personStyles = personStyles;
        LogUtils.d("fxl", personStyles.toString());
        this.inflater = LayoutInflater.from(context);

    }


    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public int getCount() {
        return personStyles.size();
    }

    @Override
    public Object getItem(int item) {
        return item;
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);
            vh = new ViewHolder();
            vh.tvNear = (TextView) convertView.findViewById(R.id.item_tv_near);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //// TODO: 2016/5/29 选中后修改背景颜色
        vh.tvNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!v.isSelected()) {
                    if (count < 3) {
                        v.setSelected(true);
                        ((TextView) v).setTextColor(Color.WHITE);
                        count++;
                        if (callBack != null) {
                            callBack.onSelect(personStyles.get(position));
                        }
                    } else {
                        ToastUtil.showToast("您只能选择3个");
                    }
                } else {
                    count--;
                    v.setSelected(false);
                    if (callBack != null) {
                        callBack.unSelect(personStyles.get(position));
                        ((TextView) v).setTextColor(Color.GRAY);
                    }
                }
            }
        });
        vh.tvNear.setText(personStyles.get(position).baseName);
        return convertView;
    }

    public class ViewHolder {
        TextView tvNear;
    }

    public interface CallBack {
        void onSelect(LuoheTagBean luoheTagBean);

        void unSelect(LuoheTagBean luoheTagBean);
    }
}
