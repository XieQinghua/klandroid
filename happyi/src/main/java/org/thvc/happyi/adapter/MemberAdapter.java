package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.thvc.happyi.R;

/**
 * Created by huangxinqi on 2016/4/7.
 */
public class MemberAdapter extends BaseAdapter {
    private Context context;
    private boolean isSafe;
    private ViewHolder viewHolder;
    private int num;
    public MemberAdapter(Context context,int num,boolean isSafe){
        this.isSafe=isSafe;
        this.num=num;
        this.context=context;
    }

    @Override
    public int getCount() {
        return num;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_member,null);
            viewHolder.et_job= (EditText) convertView.findViewById(R.id.et_job);
            viewHolder.radioGroup= (RadioGroup) convertView.findViewById(R.id.rg_gender);
            viewHolder.rbMale= (RadioButton) convertView.findViewById(R.id.rb_male);
            viewHolder.rbFemale= (RadioButton) convertView.findViewById(R.id.rb_female);
            viewHolder.et_name= (EditText) convertView.findViewById(R.id.et_name);
            viewHolder.et_phone= (EditText) convertView.findViewById(R.id.et_phone);
            viewHolder.tv_member= (TextView) convertView.findViewById(R.id.tv_member);
            viewHolder.ll_gender= (LinearLayout) convertView.findViewById(R.id.ll_gender);
            viewHolder.ll_idCard= (LinearLayout) convertView.findViewById(R.id.ll_idCard);
            viewHolder.et_idCard= (EditText) convertView.findViewById(R.id.et_idCard);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (isSafe){
            viewHolder.ll_idCard.setVisibility(View.VISIBLE);
        }
        viewHolder.rbMale.setChecked(true);
        viewHolder.tv_member.setText("成员"+(position+1));
        return convertView;
    }

    class ViewHolder{
        LinearLayout ll_idCard;
        TextView tv_member;
        EditText et_name,et_phone,et_job,et_idCard;
        LinearLayout ll_gender;
        RadioGroup radioGroup;
        RadioButton rbMale,rbFemale;
    }

}
