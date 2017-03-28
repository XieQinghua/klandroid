package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.RefundRecordBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2015/12/21.
 */
public class RefundRecordAdapter extends BaseAdapter {
    private Context context;
    private List<RefundRecordBean.DataEntity.ListEntity> list;

    public RefundRecordAdapter(Context context, List<RefundRecordBean.DataEntity.ListEntity> list) {
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
        View view = null;
        if (convertView == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_refundrecord, null);
            vh.iv_img = (CircleImageView) view.findViewById(R.id.iv_img);
            vh.tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
            vh.tv_liyou = (TextView) view.findViewById(R.id.tv_liyou);
            vh.tv_state = (TextView) view.findViewById(R.id.tv_state);
            view.setTag(vh); // 把缓存对象加到item 视图上面
        } else {
            view = convertView; // 直接复用已经不可见的视图
            vh = (ViewHolder) convertView.getTag();// 拿到缓存组件
        }
        ImgUtils.setHeadImage(vh.iv_img, list.get(position).getHeadpic());
        vh.tv_nickname.setText(list.get(position).getRealname());
        vh.tv_liyou.setText("来自" + list.get(position).getTitle());
        if (list.get(position).getIsdel().equals("1")) {
        vh.tv_state.setText("已完成");
        }else if (list.get(position).getIsdel().equals("2")||list.get(position).getIsdel().equals("3")){
            vh.tv_state.setText("申请中");
        }else if (list.get(position).getIsdel().equals("4")){
            vh.tv_state.setText("已拒绝");
        }
        return view;
    }
    class ViewHolder {
        CircleImageView iv_img;
        TextView tv_nickname;
        TextView tv_liyou;
        TextView tv_state;
    }
}
