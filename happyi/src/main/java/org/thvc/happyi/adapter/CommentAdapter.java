package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.bean.CommentBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CircleImageView;

import java.util.ArrayList;

/**
 * Created by huangxinqi
 * on 2015/11/28-17:47.
 */
public class CommentAdapter extends BaseAdapter {
    private ArrayList<CommentBean.DataEntity.ListEntity> list;
    private Context context;
    private ViewHolder v;

    public CommentAdapter(Context context, ArrayList<CommentBean.DataEntity.ListEntity> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            v = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            v.tvNickName = (TextView) view.findViewById(R.id.tv_nickname);
            v.tvTimeBefore = (TextView) view.findViewById(R.id.tv_time_before);
            v.tvCotent = (TextView) view.findViewById(R.id.tv_content);
            v.iv_avatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
            view.setTag(v);
        } else {
            v = (ViewHolder) view.getTag();
        }
        v.tvCotent.setText(list.get(i).getContent());
        v.tvNickName.setText(list.get(i).getNickname());
        v.tvTimeBefore.setText(list.get(i).getTimeBefore());
        ImgUtils.setCircleImageView(v.iv_avatar, list.get(i).getHeadpic());


        return view;
    }

    class ViewHolder {
        CircleImageView iv_avatar;
        TextView tvNickName, tvCotent, tvTimeBefore;
    }
}
