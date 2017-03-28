package org.thvc.happyi.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.HomeIndexBean;
import org.thvc.happyi.utils.ImgUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 精品活动listview适配器
 * Created by Administrator on 2016/4/7.
 */
public class RecListAdapter  extends BaseAdapter{
    private Context context;
    private ArrayList<HomeIndexBean.DataEntity.RecEntity.RecDataEntity>list;

    public RecListAdapter(Context context, ArrayList<HomeIndexBean.DataEntity.RecEntity.RecDataEntity> list) {
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_recommend_party, null);
            vh.iv_party_subject = (ImageView) convertView.findViewById(R.id.iv_party_subject);
            vh.tv_party_label1 = (TextView) convertView.findViewById(R.id.tv_party_label1);
            vh.tv_party_label2 = (TextView) convertView.findViewById(R.id.tv_party_label2);
            vh.tv_party_label3 = (TextView) convertView.findViewById(R.id.tv_party_label3);

            vh.tv_party_name = (TextView) convertView.findViewById(R.id.tv_party_name);
            vh.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            vh.tv_deadline = (TextView) convertView.findViewById(R.id.tv_deadline);
            vh.tv_party_money = (TextView) convertView.findViewById(R.id.tv_party_money);
            vh.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            vh.tv_number = (TextView) convertView.findViewById(R.id.tv_number);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        ViewGroup.LayoutParams para = vh.iv_party_subject.getLayoutParams();
        para.width = wm.getDefaultDisplay().getWidth();
        para.height = wm.getDefaultDisplay().getWidth() * 2 / 3;//活动主题图片长高设置为屏幕宽度2/3
        vh.iv_party_subject.setLayoutParams(para);
        //设置主题图片
        ImgUtils.setRectangleImage(vh.iv_party_subject, list.get(position).getSubject());

        if (list.get(position).getStatuscn() != null && list.get(position).getStatuscn().size() != 0) {
            for (int i = 0; i < list.get(position).getStatuscn().size(); i++) {
                if (list.get(position).getStatuscn().size() == 3) {
                    vh.tv_party_label1.setVisibility(View.VISIBLE);
                    vh.tv_party_label2.setVisibility(View.VISIBLE);
                    vh.tv_party_label3.setVisibility(View.VISIBLE);
                    if (i == 0) {
                        vh.tv_party_label1.setText(list.get(position).getStatuscn().get(i).getTitle());
                    } else if (i == 1) {
                        vh.tv_party_label2.setText(list.get(position).getStatuscn().get(i).getTitle());
                    } else if (i == 2) {
                        vh.tv_party_label3.setText(list.get(position).getStatuscn().get(i).getTitle());
                    }
                } else if (list.get(position).getStatuscn().size() == 2) {
                    vh.tv_party_label1.setVisibility(View.VISIBLE);
                    vh.tv_party_label2.setVisibility(View.VISIBLE);
                    vh.tv_party_label3.setVisibility(View.GONE);
                    if (i == 0) {
                        vh.tv_party_label1.setText(list.get(position).getStatuscn().get(i).getTitle());
                    } else if (i == 1) {
                        vh.tv_party_label2.setText(list.get(position).getStatuscn().get(i).getTitle());
                    }
                } else if (list.get(position).getStatuscn().size() == 1) {
                    vh.tv_party_label1.setVisibility(View.VISIBLE);
                    vh.tv_party_label2.setVisibility(View.GONE);
                    vh.tv_party_label3.setVisibility(View.GONE);
                    if (i == 0) {
                        vh.tv_party_label1.setText(list.get(position).getStatuscn().get(i).getTitle());
                    }
                }
            }
        }
        vh.tv_address.setText(list.get(position).getCity());//TODO 此处数据可能需要修改
        vh.tv_party_name.setText(list.get(position).getTitle());
        vh.tv_deadline.setText(getStringTime(list.get(position).getEnrollend()));
        double money = Double.parseDouble(list.get(position).getPrefee()) - Double.parseDouble(list.get(position).getGetpre());
        vh.tv_party_money.setText("￥" + (int) money);
        if (list.get(position).getIsget().equals("2")) {
            vh.tv_money.setText("已认证");
        } else if (list.get(position).getIsget().equals("3")) {
            vh.tv_money.setText("已认证");
        } else {
            vh.tv_money.setText("未认证");
        }
        vh.tv_number.setText("·  " + list.get(position).getCollect() + "人喜欢");
        return convertView;
    }

    class ViewHolder {
        ImageView iv_party_subject;//活动主题图片
        TextView tv_party_label1;//活动标签
        TextView tv_party_label2;
        TextView tv_party_label3;
        TextView tv_party_name;
        TextView tv_address;//地址
        TextView tv_deadline;//截止时间
        TextView tv_party_money;//活动金额
        TextView tv_money;//配比金额
        TextView tv_number;//喜欢人数
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");//精确到日
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }
}
