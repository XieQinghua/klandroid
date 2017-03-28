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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 项目名称：klandroid
 * 类描述：活动详情评论Adapter
 * 创建人：谢庆华.
 * 创建时间：2016/3/29 10:23
 * 修改人：Administrator
 * 修改时间：2016/3/29 10:23
 * 修改备注：
 */
public class PartyDetailCommentAdapter extends BaseAdapter {
    private ArrayList<PartyDetailappBean.DataEntity.CommentEntity> list;
    private Context context;

    public PartyDetailCommentAdapter(Context context, ArrayList<PartyDetailappBean.DataEntity.CommentEntity> list) {
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder vh;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            vh.tvNickName = (TextView) view.findViewById(R.id.tv_nickname);
            vh.tvTimeBefore = (TextView) view.findViewById(R.id.tv_time_before);
            vh.tvContent = (TextView) view.findViewById(R.id.tv_content);
            vh.iv_avatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        vh.tvContent.setText(list.get(position).getContent());
        vh.tvNickName.setText(list.get(position).getNickname());
        vh.tvTimeBefore.setText(getStringTime(list.get(position).getAddtime()));
        ImgUtils.setCircleImageView(vh.iv_avatar, list.get(position).getHeadpic());


        return view;
    }

    class ViewHolder {
        CircleImageView iv_avatar;
        TextView tvNickName, tvContent, tvTimeBefore;
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 hh:mm");//精确到日
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }
}

