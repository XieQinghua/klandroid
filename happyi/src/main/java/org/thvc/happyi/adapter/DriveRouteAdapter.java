package org.thvc.happyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRouteResult;

import org.thvc.happyi.R;

/**
 * Created by huangxinqi on 2016/1/22.
 */
public class DriveRouteAdapter extends RouteAdapter {
    private Context context;
    private DrivingRouteResult drivingRouteResult;
    private DrivingRouteLine routeLine;
    private ViewHolder vh;
    public DriveRouteAdapter(Context context,DrivingRouteResult drivingRouteResult){
        this.context=context;
        this.drivingRouteResult=drivingRouteResult;
    }

    @Override
    public int getCount() {
        return drivingRouteResult.getRouteLines().size();
    }

    @Override
    public Object getItem(int position) {
        return drivingRouteResult.getRouteLines().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        routeLine=drivingRouteResult.getRouteLines().get(position);
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.item_route_driving,null);
            vh=new ViewHolder();
            vh.rl_drive= (RelativeLayout) convertView.findViewById(R.id.rl_drive);
            vh.tv_distance= (TextView) convertView.findViewById(R.id.tv_distance);
            vh.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            vh.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(vh);
        }
        else{
            vh= (ViewHolder) convertView.getTag();
        }
        vh.tv_title.setText(getTitle(routeLine));
        vh.tv_distance.setText(distanceFormat(routeLine.getDistance()));
        vh.tv_time.setText(timeFormat(routeLine.getDuration()));
        return convertView;
    }

    class ViewHolder {
        TextView tv_distance, tv_title, tv_time;
        RelativeLayout rl_drive;
    }

    private String getTitle(DrivingRouteLine routeLine){
        String title="";
        DrivingRouteLine.DrivingStep step=routeLine.getAllStep().get(0);
        title=step.getEntranceInstructions()+step.getExitInstructions();
        return title;
    }

}
