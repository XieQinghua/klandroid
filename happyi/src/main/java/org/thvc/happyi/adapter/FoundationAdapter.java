package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.Foundationlistbean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/21.
 */
public class FoundationAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Foundationlistbean.DataEntity.ListEntity>list;

    public FoundationAdapter(Context context, ArrayList<Foundationlistbean.DataEntity.ListEntity> list) {
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
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ngo_intro, null);
            vh.iv_Foundation_img = (CircleImageView) convertView.findViewById(R.id.iv_ngo_img);
            vh.tv_Foundation_name = (TextView) convertView.findViewById(R.id.tv_ngo_name);
            vh.tv_Foundation_fans = (TextView) convertView.findViewById(R.id.tv_ngo_fans);
            vh.tv_Foundation_partys = (TextView) convertView.findViewById(R.id.tv_ngo_partys);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();// 拿到缓存组件
        }
        ImgUtils.setHeadImage(vh.iv_Foundation_img, list.get(position).getHeadpic());
        vh.tv_Foundation_name.setText(list.get(position).getNickname());
        vh.tv_Foundation_fans.setText(list.get(position).getCollect());
        vh.tv_Foundation_partys.setText(list.get(position).getPartycount());
        return convertView;
    }

    class ViewHolder {
        CircleImageView iv_Foundation_img;
        TextView tv_Foundation_name, tv_Foundation_fans, tv_Foundation_partys;//TextView  tv_ngo_content,cancelCollect,collect;
    }
}
