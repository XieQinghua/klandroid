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
import android.widget.TextView;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.PartyNativeDetailActivity;
import org.thvc.happyi.bean.SearchListBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/23.
 */
public class SearchListAdapter extends BaseAdapter {
    private ArrayList<SearchListBean.DataEntity> searchlist;
    private Context context;
    private String searstr;

    public SearchListAdapter(ArrayList<SearchListBean.DataEntity> searchlist, Context context, String searstr) {
        this.searchlist = searchlist;
        this.context = context;
        this.searstr = searstr;
    }

    @Override
    public int getCount() {
        return searchlist.size();
    }

    @Override
    public Object getItem(int position) {
        return searchlist.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_list, null);
            vh.tv_search = (TextView) convertView.findViewById(R.id.tv_search);
            vh.tv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PartyNativeDetailActivity.class);
                    intent.putExtra("id", searchlist.get(position).getId());
                    context.startActivity(intent);
                }
            });
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        String title = searchlist.get(position).getTitle();
            int bstart = title.indexOf(searstr);
            int bend = bstart + searstr.length();
        SpannableStringBuilder style = new SpannableStringBuilder(title);
        if (bstart >= 0) {
            style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orangered)),
                    bstart, bend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            vh.tv_search.setText(style);
        } else {
            vh.tv_search.setText(title);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_search;
    }
}
