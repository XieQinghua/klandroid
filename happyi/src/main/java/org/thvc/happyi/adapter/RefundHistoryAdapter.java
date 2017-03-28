package org.thvc.happyi.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.RefundHistoryBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/18.
 */
public class RefundHistoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RefundHistoryBean.DataEntity.ListEntity> listEntities;

    public RefundHistoryAdapter(Context context, ArrayList<RefundHistoryBean.DataEntity.ListEntity> listEntities) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_refundhistory, null);
            vh.tv_collect = (TextView) view.findViewById(R.id.tv_collect);
            vh.im_user_img = (CircleImageView) view.findViewById(R.id.im_user_img);
            vh.tv_party_title = (TextView) view.findViewById(R.id.tv_party_title);
            vh.tv_reason = (TextView) view.findViewById(R.id.tv_reason);
            vh.tv_party_time = (TextView) view.findViewById(R.id.tv_party_time);
            vh.tv_Refund_amount = (TextView) view.findViewById(R.id.tv_Refund_amount);
            vh.iv_user_name = (TextView) view.findViewById(R.id.iv_user_name);
            view.setTag(vh); // 把缓存对象加到item 视图上面
        } else {
            view = convertView; // 直接复用已经不可见的视图
            vh = (ViewHolder) convertView.getTag();// 拿到缓存组件
        }
        String collect = listEntities.get(position).getIsdel();
        if (collect.equals("3")) {
            vh.tv_collect.setText("已同意");
            vh.tv_collect.setTextColor(context.getResources().getColor(R.color.green));
        } else if (collect.equals("4")) {
            vh.tv_collect.setText("已拒绝");
            vh.tv_collect.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            vh.tv_collect.setText("退款中");
            vh.tv_collect.setTextColor(context.getResources().getColor(R.color.orangered));
        }
        ImgUtils.setHeadImage(vh.im_user_img, listEntities.get(position).getHeadpic());
        vh.iv_user_name.setText(listEntities.get(position).getNickname());
        vh.tv_party_title.setText(listEntities.get(position).getTitle());
        String searstr = listEntities.get(position).getReason();
        String title = "退款原因：" + listEntities.get(position).getReason();
        int bstart = title.indexOf(searstr);
        int bend = bstart + searstr.length();
        SpannableStringBuilder style = new SpannableStringBuilder(title);
        style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orangered)), bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        vh.tv_reason.setText(style);

        vh.tv_party_time.setText("报名截止时间：" + getStringTime(listEntities.get(position).getEnrollend()));
        vh.tv_Refund_amount.setText("金额：" + listEntities.get(position).getPayfee());

        String searstrs = listEntities.get(position).getPayfee();
        String titles = "金额：" + listEntities.get(position).getPayfee();
        int bstarts = titles.indexOf(searstrs);
        int bends = bstarts + searstrs.length();
        SpannableStringBuilder styles = new SpannableStringBuilder(titles);
        styles.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orangered)), bstarts, bends, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        vh.tv_Refund_amount.setText(styles);

        return view;
    }

    class ViewHolder {
        CircleImageView im_user_img;
        TextView tv_collect;
        TextView iv_user_name;
        TextView tv_party_title;
        TextView tv_reason;
        TextView tv_party_time;
        TextView tv_Refund_amount;
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD");
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }
}
