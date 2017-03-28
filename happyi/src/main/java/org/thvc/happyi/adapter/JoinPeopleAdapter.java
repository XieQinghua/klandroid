package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.PartyDetailappBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;

/**
 * 项目名称：klandroid
 * 类描述：
 * 创建人：谢庆华.
 * 创建时间：2016/3/30 14:33
 * 修改人：Administrator
 * 修改时间：2016/3/30 14:33
 * 修改备注：
 */
public class JoinPeopleAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PartyDetailappBean.DataEntity.JoinpeopleEntity> list;
    private String str;

    public JoinPeopleAdapter(Context context, ArrayList<PartyDetailappBean.DataEntity.JoinpeopleEntity> list, String str) {
        this.context = context;
        this.list = list;
        this.str = str;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 1;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {

        if (list == null) {
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        if (list == null) {
            return 0;
        } else {
            return position;
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_join_people, null);
            vh.iv_ngo_img = (CircleImageView) convertView.findViewById(R.id.iv_ngo_img);
            vh.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (list == null) {
            vh.iv_ngo_img.setImageResource(R.drawable.icon_head_empty);
            vh.tv_number.setVisibility(View.VISIBLE);
            vh.tv_number.setText("0");
        } else if (list.size() <= 7) {
            if ((position + 1) == list.size()) {
                vh.iv_ngo_img.setImageResource(R.drawable.icon_head_empty);
                vh.tv_number.setVisibility(View.VISIBLE);
                vh.tv_number.setText(str);
            } else {
                ImgUtils.setHeadImage(vh.iv_ngo_img, list.get(position).getHeadpic());
            }
        } else {
            if (position == 6) {
                vh.iv_ngo_img.setImageResource(R.drawable.icon_head_empty);
                vh.tv_number.setVisibility(View.VISIBLE);
                vh.tv_number.setText(str);
            } else {
                ImgUtils.setHeadImage(vh.iv_ngo_img, list.get(position).getHeadpic());
            }
        }
        return convertView;
    }

    private class ViewHolder {
        CircleImageView iv_ngo_img;
        TextView tv_number;
    }
}
