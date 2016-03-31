package com.luohe.android.luohe.utils.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;

import java.util.List;

import butterknife.ButterKnife;


public class ShareGridViewAdapter extends BaseAdapter {

    private List<ShareItem> itemBeans;
    private Context mContext;

    public ShareGridViewAdapter(Context mContext, List<ShareItem> itemBeans) {
        this.mContext = mContext;
        this.itemBeans = itemBeans;
    }

    @Override
    public int getCount() {
        return itemBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return itemBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (null == convertView)
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.share_gridview_item, null);
        ImageView imageView = ButterKnife.findById(convertView, R.id.civ_logo);
        TextView title = (TextView) convertView.findViewById(R.id.tv_title);
        ShareItem item = (ShareItem) getItem(position);

        imageView.setImageResource(item.getIcon());
        title.setText(item.getName());
        return convertView;
    }

    public void addView(ShareItem item) {
        itemBeans.add(item);
        notifyDataSetChanged();
    }

    public List<ShareItem> getData() {
        return itemBeans;
    }
}
