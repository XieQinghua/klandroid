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
 * 项目名称：klandroid
 * 创建时间：2015/11/12 11:41
 * 修改人：Administrator
 * 修改时间：2015/11/12 11:41
 * 修改备注：
 */
public class PartyLabelAdapter extends BaseAdapter {
    private ArrayList<HomeIndexBean.DataEntity.HotEntity.HotDataEntity> list;
    private Context context;

    public PartyLabelAdapter(ArrayList<HomeIndexBean.DataEntity.HotEntity.HotDataEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
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