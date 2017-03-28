package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.RefundHistoryBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/1.
 */
public class RefundAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<RefundHistoryBean.DataEntity.ListEntity> listEntities;

    public RefundAdapter(Context context, ArrayList<RefundHistoryBean.DataEntity.ListEntity> listEntities) {
        this.context = context;
        this.listEntities = listEntities;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        View view = null;
        if (convertView == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_refund, null);
            vh.iv_img = (CircleImageView) view.findViewById(R.id.iv_img);
            vh.tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
            vh.tv_liyou = (TextView) view.findViewById(R.id.tv_liyou);
            view.setTag(vh); // 把缓存对象加到item 视图上面
        } else {
            view = convertView; // 直接复用已经不可见的视图
            vh = (ViewHolder) convertView.getTag();// 拿到缓存组件
        }
        ImgUtils.setHeadImage(vh.iv_img, listEntities.get(position).getHeadpic());
        vh.tv_nickname.setText(listEntities.get(position).getNickname());
        vh.tv_liyou.setText("来自" + listEntities.get(position).getTitle());
        return view;
    }

    class ViewHolder {
        CircleImageView iv_img;
        TextView tv_nickname;
        TextView tv_liyou;
    }
}
