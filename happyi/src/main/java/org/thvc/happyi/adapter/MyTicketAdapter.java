package org.thvc.happyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.PartyNativeDetailActivity;
import org.thvc.happyi.bean.MyTicketBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/1/29.
 */
public class MyTicketAdapter extends BaseAdapter {
    private ArrayList<MyTicketBean.DataEntity.ListEntity> list;
    private Context context;

    public MyTicketAdapter(ArrayList<MyTicketBean.DataEntity.ListEntity> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ticket, null);
            vh.iv_img = (CircleImageView) convertView.findViewById(R.id.iv_img);
            vh.tv_party_title = (TextView) convertView.findViewById(R.id.tv_party_title);
            vh.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            vh.iv_name = (TextView) convertView.findViewById(R.id.iv_name);
            vh.iv_money = (TextView) convertView.findViewById(R.id.iv_money);
            vh.im_qcode = (ImageView) convertView.findViewById(R.id.im_qcode);
            vh.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.RelativeLayout);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();// 拿到缓存组件
        }
        vh.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PartyNativeDetailActivity.class);
                intent.putExtra("id", list.get(position).getDataid());
                context.startActivity(intent);
            }
        });
        ImgUtils.setHeadImage(vh.iv_img, list.get(position).getHeadpic());
        vh.tv_party_title.setText(list.get(position).getPartytitle());
        vh.tv_time.setText("报名时间：" + getStringTime(list.get(position).getAddtime()));
        vh.iv_name.setText(list.get(position).getNickname());
        String Payfee = list.get(position).getPayfee();
        String newPayfee = Payfee.replaceAll(".00", "");
        String money = "已支付活动费用" + newPayfee + "元";
        int bstart = money.indexOf(newPayfee);
        int bend = bstart + newPayfee.length();
        SpannableStringBuilder style = new SpannableStringBuilder(money);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orangered)), bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        vh.iv_money.setText(style);
        ImgUtils.setRectangleImages(vh.im_qcode, list.get(position).getQcode());
        return convertView;
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//精确到日
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }

    class ViewHolder {
        CircleImageView iv_img;
        TextView tv_party_title, tv_time, iv_name, iv_money;
        ImageView im_qcode;
        RelativeLayout relativeLayout;
    }
}
