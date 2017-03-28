package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.JoinPeopleBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by huangxinqi on 2016/1/6.
 */
public class PartyPeopleAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<JoinPeopleBean.DataEntity.ListEntity> list;

    public PartyPeopleAdapter(Context context, ArrayList<JoinPeopleBean.DataEntity.ListEntity> list) {
        this.list = list;
        this.context = context;
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
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_more_join_people, null);
            vh.civ_head_pic = (CircleImageView)convertView.findViewById(R.id.civ_head_pic);
            vh.tv_join_time = (TextView) convertView.findViewById(R.id.tv_join_time);
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            vh.tv_payfee = (TextView) convertView.findViewById(R.id.tv_payfee);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ImgUtils.setHeadImage( vh.civ_head_pic, list.get(position).getHeadpic());
        vh.tv_join_time.setText(list.get(position).getTime());
        vh.tv_payfee.setText("支付了" + list.get(position).getPayfee() + "元参与活动");
        if (list.get(position).getRealname().equals("")) {
            vh.tv_name.setText("匿名用户");
        } else {
            vh.tv_name.setText(list.get(position).getRealname());
        }
        return convertView;
    }

    private class ViewHolder {
        CircleImageView civ_head_pic;
        TextView tv_name, tv_payfee, tv_join_time;
    }

    /**
     * 将真实姓名的姓保留，名字换成*
     *
     * @param realName
     * @return
     */
    private String hideRealName(String realName) {
        return realName.substring(0, 1) + "**";
    }
}
