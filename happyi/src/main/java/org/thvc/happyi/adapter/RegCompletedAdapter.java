package org.thvc.happyi.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.RegisterInformationBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/30.
 */
public class RegCompletedAdapter extends BaseAdapter {

    private List<RegisterInformationBean.DataEntity.ListEntity> list;
    private Context context;

    public RegCompletedAdapter(List<RegisterInformationBean.DataEntity.ListEntity> list, Context context) {
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
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_informations, null);
            vh.iv_my_img = (CircleImageView) convertView.findViewById(R.id.iv_my_img);
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            vh.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            vh.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            vh.tv_ispay = (TextView)convertView.findViewById(R.id.tv_ispay);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();// 拿到缓存组件
        }
        if (list.get(position).getIspay().equals("1")) {
            vh.tv_ispay.setText("付款成功：");
            Resources resource =  context.getResources();
            ColorStateList colorStateList = resource.getColorStateList(R.color.green);
            vh.tv_money.setTextColor(colorStateList);
            vh.tv_money.setText("支付了" + list.get(position).getPayfee() + "元");
        } else if (list.get(position).getIspay().equals("2")) {
            vh.tv_ispay.setText("未付款：");
            vh.tv_money.setText("需支付" + list.get(position).getPayfee() + "元");
            Resources resource =  context.getResources();
            ColorStateList colorStateList = resource.getColorStateList(R.color.red);
            vh.tv_money.setTextColor(colorStateList);
        }
        vh.tv_name.setText(list.get(position).getRealname());
        vh.tv_time.setText(list.get(position).getTime());
        ImgUtils.setHeadImage(vh.iv_my_img, list.get(position).getHeadpic());
        return convertView;
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }


    public class ViewHolder {
        CircleImageView iv_my_img;
        TextView tv_name;
        TextView tv_money;
        TextView tv_time;
        TextView tv_ispay;

    }
}
