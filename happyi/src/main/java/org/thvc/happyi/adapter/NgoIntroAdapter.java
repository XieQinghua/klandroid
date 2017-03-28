package org.thvc.happyi.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.application.HappyiApplication;
import org.thvc.happyi.bean.NgoListBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;


/**
 * 颜松梁
 * Created by Administrator on 2015/11/13.
 * NGO列表适配器
 */
public class NgoIntroAdapter extends BaseAdapter {

    private ArrayList<NgoListBean.DataEntity.ListEntity> listEntities;
    private Context context;
    private String userid;
    private int hascollect;
    private int Collect = 0;
    private Handler handler;

    public NgoIntroAdapter(ArrayList<NgoListBean.DataEntity.ListEntity> listEntities, Context context, Handler handler) {
        this.listEntities = listEntities;
        this.context = context;
        this.handler = handler;
        userid = HappyiApplication.getInstance().getUserid(context);
    }

    @Override
    public int getCount() {
        return listEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return listEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ngo_intro, null);
            vh.iv_ngo_img = (CircleImageView) convertView.findViewById(R.id.iv_ngo_img);
            vh.tv_ngo_name = (TextView) convertView.findViewById(R.id.tv_ngo_name);
            vh.tv_ngo_fans = (TextView) convertView.findViewById(R.id.tv_ngo_fans);
            vh.tv_ngo_partys = (TextView) convertView.findViewById(R.id.tv_ngo_partys);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();// 拿到缓存组件
        }
        ImgUtils.setHeadImage(vh.iv_ngo_img, listEntities.get(position).getHeadpic());
        vh.tv_ngo_name.setText(listEntities.get(position).getNickname());
        vh.tv_ngo_fans.setText(listEntities.get(position).getCollect());
        vh.tv_ngo_partys.setText(listEntities.get(position).getPartycount());
        return convertView;
    }

    class ViewHolder {
        CircleImageView iv_ngo_img;
        TextView tv_ngo_name, tv_ngo_fans, tv_ngo_partys;//TextView  tv_ngo_content,cancelCollect,collect;
    }
}
