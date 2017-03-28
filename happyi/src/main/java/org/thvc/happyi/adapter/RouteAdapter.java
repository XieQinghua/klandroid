package org.thvc.happyi.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by huangxinqi on 2016/1/21.
 * 所有路径规划的adapter集成这个adapter
 */
public class RouteAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 0;
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
        return null;
    }

    protected String timeFormat(int second){
        String result;
        int h = 0;
        int m = 0;
        int s = 0;
        int temp = second%3600;
        if(second>3600){
            h= second/3600;
            if(temp!=0){
                if(temp>60){
                    m = temp/60;
                    if(temp%60!=0){
                        s = temp%60;
                    }
                }else{
                    s = temp;
                }
            }
            result=h+"时"+m+"分"+s+"秒";
        }else{
            m = second/60;
            if(second%60!=0){
                s = second%60;
            }
            result=m+"分"+s+"秒";
        }

        return result;
    }
    protected String distanceFormat(int meter){
        String result;
        if (meter<1000){
            result=meter+"米";
        }
        else{
            result=Math.rint(meter/100)/10+"千米";
        }
        return result;
    }


}
