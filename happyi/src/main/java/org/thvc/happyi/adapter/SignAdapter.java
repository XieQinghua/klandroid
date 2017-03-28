package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.SignBean;
import org.thvc.happyi.utils.Constants;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/31.
 */
public class SignAdapter extends BaseAdapter {

    private ArrayList<SignBean.DataBean.ListBean> list;
    private Context context;

    public SignAdapter(ArrayList<SignBean.DataBean.ListBean> list, Context context) {
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
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.iten_sign, null);
            vh.iv_avatar = (CircleImageView)convertView.findViewById(R.id.iv_avatar);
            vh.tv_nickname = (TextView)convertView.findViewById(R.id.tv_nickname);
            vh.tv_comment = (TextView)convertView.findViewById(R.id.tv_comment);
            vh.tv_sign = (TextView)convertView.findViewById(R.id.tv_sign);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
//        ImgUtils.setHeadImage(vh.iv_avatar, list.get(position).getHeadpic());
        vh.tv_nickname.setText(list.get(position).getNickname());
        vh.tv_comment.setText(list.get(position).getFirst());
        if (list.get(position).getScan().equals("0")){
            vh.tv_sign.setText("未签到");
        }else if (list.get(position).getScan().equals("1")){
            vh.tv_sign.setText("已签到");
        }
        return convertView;
    }

    private class ViewHolder {
        CircleImageView iv_avatar;
        TextView tv_nickname,tv_comment,tv_sign;
    }
}
