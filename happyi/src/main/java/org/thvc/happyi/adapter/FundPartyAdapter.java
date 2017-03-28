package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.ClaimPartyBean;
import org.thvc.happyi.utils.ImgUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/23.
 */
public class FundPartyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ClaimPartyBean.DataEntity.ListEntity> list;

    public FundPartyAdapter(Context context, ArrayList<ClaimPartyBean.DataEntity.ListEntity> list) {
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
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ngo_party, null);
            vh.iv_party_subject = (ImageView) convertView.findViewById(R.id.iv_party_subject);
            vh.tv_party_name = (TextView) convertView.findViewById(R.id.tv_party_name);
            vh.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            vh.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            vh.tv_fans = (TextView) convertView.findViewById(R.id.tv_fans);
            vh.tv_party_label1 = (TextView) convertView.findViewById(R.id.tv_party_label1);
            vh.tv_party_label2 = (TextView) convertView.findViewById(R.id.tv_party_label2);
            vh.tv_party_label3 = (TextView) convertView.findViewById(R.id.tv_party_label3);
            vh.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ImgUtils.setRectangleImage(vh.iv_party_subject, list.get(position).getSubject());
        vh.tv_address.setText(list.get(position).getCity());
        vh.tv_party_name.setText(list.get(position).getTitle());
        vh.tv_time.setText(getStringTime(list.get(position).getEnrollend()));
        vh.tv_fans.setText("· " + list.get(position).getCollect() + "人喜欢");
        double money = Double.parseDouble(list.get(position).getPrefee()) - Double.parseDouble(list.get(position).getGetpre());
        vh.tv_money.setText("￥" + (int) money);
        return convertView;
    }
    class ViewHolder {
        ImageView iv_party_subject;
        TextView tv_party_name;
        TextView tv_address;
        TextView tv_time;
        TextView tv_fans;
        TextView tv_party_label1, tv_party_label2, tv_party_label3;
        TextView tv_money;
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");//精确到日
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }
}
