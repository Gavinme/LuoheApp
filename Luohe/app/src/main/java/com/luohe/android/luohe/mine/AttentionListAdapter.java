package com.luohe.android.luohe.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.net.model.AttenBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by XinLe on 2016/6/8.
 * 关注用户列表Adapter
 */
public class AttentionListAdapter extends BaseAdapter {
    private Context context;
    private List<AttenBean> attenBeenList;
    private LayoutInflater inflater;

    public AttentionListAdapter(Context context, List<AttenBean> attenBeen) {
        this.context = context;
        this.attenBeenList = attenBeen;
        this.inflater =LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

            return attenBeenList.size();

    }

    @Override
    public AttenBean getItem(int position) {
        return attenBeenList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_attention_listview,parent,false);
            vh.tvName = (TextView) convertView.findViewById(R.id.item_attention_name);
            vh.ivAvatar = (ImageView) convertView.findViewById(R.id.item_attention_avatar);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvName.setText(attenBeenList.get(position).nickName);
       ImageLoader.getInstance().displayImage(getItem(position).headUrl, vh.ivAvatar);
        return convertView;
    }
    class ViewHolder{
        TextView tvName;
        ImageView ivAvatar;
    }
}
