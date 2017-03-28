package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.HomeIndexBean;
import org.thvc.happyi.utils.ImgUtils;

import java.util.ArrayList;

/**
 * 最新活动Gridview适配器
 * Created by Administrator on 2016/4/7.
 */
public class NewGridListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<HomeIndexBean.DataEntity.NewEntity.NewDataEntity>list;

    public NewGridListAdapter(Context context, ArrayList<HomeIndexBean.DataEntity.NewEntity.NewDataEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_party_label, null);
            vh.im_special = (ImageView) convertView.findViewById(R.id.im_special);
            vh.im_party_tite = (TextView) convertView.findViewById(R.id.im_party_tite);
            vh.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            vh.tv_fans = (TextView) convertView.findViewById(R.id.tv_fans);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ImgUtils.setRectangleImage(vh.im_special, list.get(position).getSubject());
        vh.im_party_tite.setText(list.get(position).getTitle());
        double money = Double.parseDouble(list.get(position).getPrefee());
        vh.tv_money.setText("￥" + (int) money);
        vh.tv_fans.setText(list.get(position).getCollect());
        return convertView;
    }

    class ViewHolder {
        ImageView im_special;
        TextView im_party_tite;
        TextView tv_money;
        TextView tv_fans;

    }
}
